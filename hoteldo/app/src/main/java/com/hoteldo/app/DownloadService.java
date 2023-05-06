package com.hoteldo.app;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DownloadService extends Service {
    private ArrayList<Order> orders;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        orders = (ArrayList<Order>) intent.getSerializableExtra("orders");
        performWriteOperations();
        //return super.onStartCommand(intent, flags, startId);
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void performWriteOperations() {
        if (isExternalStorageWritable()) {
            File dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS); // Get the app's private Downloads directory
            File file = new File(dir, "orders.csv");
            try {
                FileWriter writer = new FileWriter(file);
                // Write header row to CSV file
                writer.append("Order ID,User ID,Hotel ID,Room ID,Arrival Date,Departure Date,Guest Mail,Guest Name\n");
                // Loop through orders ArrayList and write data to CSV file
                for (Order order : orders) {
                    writer.append(order.getOrderID());
                    writer.append(",");
                    writer.append(order.getUserID());
                    writer.append(",");
                    writer.append(order.getHotelID());
                    writer.append(",");
                    writer.append(order.getRoomID());
                    writer.append(",");
                    writer.append(order.getArrivalDate().toString());
                    writer.append(",");
                    writer.append(order.getDepartureDate().toString());
                    writer.append(",");
                    writer.append(order.getGuestMail());
                    writer.append(",");
                    writer.append(order.getGuestName());
                    writer.append("\n");
                }
                // Close FileWriter object
                writer.flush();
                writer.close();
                // Get the URI for the file using FileProvider
                Uri fileUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", file);
                if (fileUri != null) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_STREAM, fileUri);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Intent chooserIntent = Intent.createChooser(intent, "Share File");
                    chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(chooserIntent);
                    Toast.makeText(getApplicationContext(), "CSV file downloaded successfully", Toast.LENGTH_SHORT).show();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "File Not Created!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "External storage is not writable", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}