import time
import firebase_admin
from firebase_admin import credentials
import joblib
from firebase_admin import initialize_app
import pandas as pd
import numpy as np
from sklearn.preprocessing import FunctionTransformer

# Initialize the Firebase Admin SDK
cred = credentials.Certificate('F:\path\emergency-alert-system-final-firebase-adminsdk-xl6le-a6b9e78b52.json')
firebase_admin.initialize_app(cred, {
    'databaseURL': 'https://emergency-alert-system-final-default-rtdb.firebaseio.com/'
})

# Load the trained model

trained_model = joblib.load('F:\\path\\trained_model (13).pkl')

from firebase_admin import db

# Get a reference to the Realtime Database
ref = db.reference()

# Function to classify severity
def classify_severity(record):
    # Create a DataFrame with the input record
    record_df = pd.DataFrame([record])

    # Reorder the columns in the DataFrame
    desired_order = ['Age', 'Body Temperature (°C)', 'Heart Rate (BPM)', 'spo2', 'HeartRate Log']
    record_df = record_df[desired_order]

    record_df['HeartRate Log'] = np.log1p(record_df['Heart Rate (BPM)'])
    

    print(record_df)
    # Predict the severity
    predicted_severity = trained_model.predict(record_df)

    return predicted_severity[0]


# Read the data from Realtime Database
def read_data():
    record_data = ref.get()
    if record_data:
        print(record_data)
        severity = classify_severity(record_data)
        print(severity)
        # Update severity attribute in the Realtime Database
        ref.update({'severity': severity})
        print("Severity attribute updated.")
    else:
        print("No data found in the Realtime Database.")

# Run the classification every 30 seconds
def classify_data():
    while True:
        read_data()
        time.sleep(15)  # Sleep for 30 seconds


# Start the classification process
classify_data()
