package id.slavant.taxidbtest

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
class Order {

    var location: String? = null
    var time: String? = null
    var date: String? = null

    constructor()

    constructor(location: String, time: String, date: String){
        this.location = location
        this.time = time
        this.date = date
    }

}

