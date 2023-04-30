package com.hoteldo.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Checkoutpage_Activity extends AppCompatActivity {

    private static ArrayList<Order> Orders;
    TextView txtPurchasedhotelname;
    TextView txtPurchasedroomname;
    TextView txtPurchasedroomprice;
    EditText etGuestname;
    EditText etGuestemail;
    EditText txtArrivalDate;
    EditText txtDeparturedate;

    TextView nightsBooked;
    TextView nightsBookedTotal;
    TextView taxTotal;
    TextView totalBill;
    Button btnCheckout;
    FirebaseAuth mAuth;
    Date departureDate;
    Date arrivalDate;
    int days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkoutpage);

        // Purchased hotel details
        Hotel hotel = (Hotel) getIntent().getSerializableExtra("myHotel");
        txtPurchasedhotelname = findViewById(R.id.txtPurchasedhotelname);
        txtPurchasedhotelname.setText(hotel.getName());

        // Purchased room name
        Room room = (Room) getIntent().getSerializableExtra("myRoom");
        txtPurchasedroomname = findViewById((R.id.txtPurchasedroomname));
        txtPurchasedroomname.setText(room.getName());
        txtPurchasedroomprice = findViewById(R.id.txtPurchasedroomprice);
        String Price = Float.toString(room.getPrice());
        Price="$"+Price+" per Night";
        txtPurchasedroomprice.setText(Price);

        // User Details
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        etGuestname = findViewById(R.id.etGuestname);
        assert currentUser != null;
        etGuestname.setText(currentUser.getDisplayName());
        etGuestemail = findViewById(R.id.etGuestemail);
        etGuestemail.setText(currentUser.getEmail());

        // arival date
        txtArrivalDate=findViewById(R.id.txtArrivalDate);
        txtArrivalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Checkoutpage_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Set the selected date in the EditText
                                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                                txtArrivalDate.setText(selectedDate);

                                String arrivalDateStr = txtArrivalDate.getText().toString();
                                // Parse the arrival date string into a Date object
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                arrivalDate = null;
                                try {
                                    arrivalDate = dateFormat.parse(arrivalDateStr);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                // calculation of payements!!
                                if(arrivalDate != null &&departureDate!=null) {
                                    // Calculate the number of days between the departure and arrival dates
                                    long timeDiff = departureDate.getTime() - arrivalDate.getTime();
                                    days = (int) TimeUnit.MILLISECONDS.toDays(timeDiff) % 365;

                                    if(days > 0){
                                        // payment calculations
                                        nightsBooked = findViewById(R.id.nightsBooked);
                                        String totalNights = days + " Nights";
                                        nightsBooked.setText(totalNights);

                                        nightsBookedTotal = findViewById(R.id.nightsBookedTotal);
                                        float nightsBookedTotalPrice = days * room.getPrice();
                                        String nightsBookedTotalPriceStr = Float.toString(nightsBookedTotalPrice);
                                        ;
                                        nightsBookedTotalPriceStr = "$" + nightsBookedTotalPriceStr;
                                        nightsBookedTotal.setText(nightsBookedTotalPriceStr);

                                        taxTotal = findViewById(R.id.taxTotal);
                                        taxTotal.setText("$10");

                                        totalBill = findViewById(R.id.totalBill);
                                        float totalbilling = nightsBookedTotalPrice + 10;
                                        String totalbillingstr = Float.toString(totalbilling);
                                        totalBill.setText(totalbillingstr);
                                    }
                                    else{
                                        Toast.makeText(Checkoutpage_Activity.this, "Must: Arrival Date < Departure Date!", Toast.LENGTH_SHORT).show();
                                        nightsBooked.setText("Nights");
                                        nightsBookedTotal.setText("$");
                                        taxTotal.setText("$");
                                        totalBill.setText("$");
                                    }
                                }
                                else{
                                    Toast.makeText(Checkoutpage_Activity.this, "Enter Date!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        // departure date
        txtDeparturedate=findViewById(R.id.txtDeparturedate);
        txtDeparturedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Checkoutpage_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Set the selected date in the EditText
                                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                                txtDeparturedate.setText(selectedDate);

                                String departureDateStr = txtDeparturedate.getText().toString();
                                // Parse the arrival date string into a Date object
                                SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
                                departureDate = null;
                                try {
                                    departureDate = dateFormat2.parse(departureDateStr);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                // calculation of payements!!
                                if(arrivalDate != null &&departureDate!=null) {
                                    // Calculate the number of days between the departure and arrival dates
                                    long timeDiff = departureDate.getTime() - arrivalDate.getTime();
                                    days = (int) TimeUnit.MILLISECONDS.toDays(timeDiff) % 365;

                                    if(days > 0){
                                        // payment calculations
                                        nightsBooked = findViewById(R.id.nightsBooked);
                                        String totalNights = days + " Nights";
                                        nightsBooked.setText(totalNights);

                                        nightsBookedTotal = findViewById(R.id.nightsBookedTotal);
                                        float nightsBookedTotalPrice = days * room.getPrice();
                                        String nightsBookedTotalPriceStr = Float.toString(nightsBookedTotalPrice);
                                        ;
                                        nightsBookedTotalPriceStr = "$" + nightsBookedTotalPriceStr;
                                        nightsBookedTotal.setText(nightsBookedTotalPriceStr);

                                        taxTotal = findViewById(R.id.taxTotal);
                                        taxTotal.setText("$10");

                                        totalBill = findViewById(R.id.totalBill);
                                        float totalbilling = nightsBookedTotalPrice + 10;
                                        String totalbillingstr = Float.toString(totalbilling);
                                        totalBill.setText(totalbillingstr);
                                    }
                                    else{
                                        Toast.makeText(Checkoutpage_Activity.this, "Must: Arrival Date < Departure Date!", Toast.LENGTH_SHORT).show();
                                        nightsBooked.setText("Nights");
                                        nightsBookedTotal.setText("$");
                                        taxTotal.setText("$");
                                        totalBill.setText("$");
                                    }
                                }
                                else{
                                    Toast.makeText(Checkoutpage_Activity.this, "Enter Date!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, year, month, day);
                datePickerDialog.show();

            }
        });
        
        // checkout button
        btnCheckout = findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(days > 0){
                    Toast.makeText(Checkoutpage_Activity.this, "Checkout Successful!", Toast.LENGTH_SHORT).show();
                    Orders = HomepageActivity.getOrders();
                    Order order = new Order(currentUser.getEmail(),hotel.getHotelID(),room.getRoomID(), arrivalDate, departureDate, etGuestemail.getText().toString(),etGuestname.getText().toString());
                    Orders.add(order);
                }
            }
        });
    }
}