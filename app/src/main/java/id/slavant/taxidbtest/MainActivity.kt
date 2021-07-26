package id.slavant.taxidbtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.StringBuilder
import kotlin.random.Random.Default.nextInt

const val TAG ="LOG_TAG"

class MainActivity : AppCompatActivity() {


    private val orderList = mutableListOf<Order>()
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var textView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Firebase.database
        databaseReference = database.getReference("bdtest")

        textView = findViewById(R.id.textView)

//        if (orderList.size == 0){
//            readFromDbtest()}

        readFromDb()
//        textView.text = orderList.size.toString()

    }

    override fun onResume() {
        super.onResume()

//        readFromDb()
        textView.text = orderList.get(0).location
//        if (orderList.size == 0){
//            readFromDbtest()}
//        else{
//            readFromDb()
//            textView.text = orderList.size.toString()
//        }

    }

    // read items change in data base
    private fun readFromDb(){

        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.key!!)

                // A new comment has been added, add it to the displayed list
//                val comment = dataSnapshot.getValue<Order>()

                val order = dataSnapshot.getValue(Order::class.java)
                if (order != null) {
                    orderList.add(order)
                }

                // ...
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildChanged: ${dataSnapshot.key}")

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
//                val newComment = dataSnapshot.getValue<Comment>()
                val commentKey = dataSnapshot.key

                // ...
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.key!!)

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                val commentKey = dataSnapshot.key

                // ...
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.key!!)

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
//                val movedComment = dataSnapshot.getValue<Comment>()
                val commentKey = dataSnapshot.key

                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException())
                Toast.makeText(this@MainActivity, "Failed to load comments.",
                    Toast.LENGTH_SHORT).show()
            }
        }
        databaseReference.addChildEventListener(childEventListener)
    }


    //gets list from database. read all database change
    private fun readFromDbtest(){

        // Read from the database
        databaseReference.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                // getting list of objects from database
                //but it gets full list every time database changes
                for (order in snapshot.children){
                    val value = order.getValue(Order::class.java)
                    if (value != null) {
                        orderList.add(value)
                    }
                }
                textView.text = orderList.size.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                textView.text = error.toException().toString()
            }
        })
    }
}