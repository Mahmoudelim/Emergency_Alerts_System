package com.example.emergency_alert_system.user.creation

data class user_location(var username:String?=null,var streetname:String?=null,
                    var naighbourrhood:String?=null
                    ,var buildingnumb: String?=null,
                     var state:String?=null,
                    var flatingnumb :String?=null,
                    var longitude:Double?=null,
                         var latitude:Double?=null,
){
}
//onot forget cuurent location
//location on home