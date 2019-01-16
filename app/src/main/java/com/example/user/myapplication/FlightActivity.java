package com.example.user.myapplication;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.example.user.myapplication.flight.Flight;
import com.example.user.myapplication.flight.FlightController;
import com.example.user.myapplication.Model.ModelViewFlights;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightActivity extends AppCompatActivity {
    private EditText textFieldNumber;
    private EditText textFieldRoute;
    private ListView listView;
    private String token;
    private FlightController ControllerFlight;
    private int inceput = 0;
    private int cate = 5;
    private Button ButtonBack;
    private Button ButtonForward;
    private ModelViewFlights viewFlights = new ModelViewFlights();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_flight);
            ButtonBack = (Button) findViewById(R.id.buttonBack);
            ButtonBack.setText("<");
            ButtonForward = (Button) findViewById(R.id.buttonForward);
            ButtonForward.setText(">");
            this.textFieldNumber = findViewById(R.id.textFiledNumber);
            this.textFieldRoute = findViewById(R.id.textFieldRoute);
            this.listView = findViewById(R.id.listView);

            synchronize();

            token=getIntent().getStringExtra("token");
            ControllerFlight = ViewModelProviders.of(this, new FlightController.Factory(getApplicationContext())).get(FlightController.class);
            viewFlights.getFlightsPaginated(inceput, cate,token).enqueue(new Callback<List<Flight>>() {
                @Override
                public void onResponse(Call<List<Flight>> call, Response<List<Flight>> response) {
                    System.out.println("M-am conectat");
                    if (response.body() != null) {
                        List<Flight> flights = response.body();
                        if (flights != null) {
                            ArrayAdapter<Flight> arrayAdapter = new ArrayAdapter<Flight>(FlightActivity.this, android.R.layout.simple_list_item_1, flights);
                            listView.setAdapter(arrayAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Flight>> call, Throwable t) {
                    System.out.println("Nu m-am conectat");
                    List<Flight> flights = ControllerFlight.getFlights(cate, inceput);
                    ArrayAdapter<Flight> arrayAdapter = new ArrayAdapter<Flight>(FlightActivity.this, android.R.layout.simple_list_item_1, flights);
                    listView.setAdapter(arrayAdapter);
                    if (flights.size() == 0)
                        ButtonForward.setEnabled(false);
                }
            });
            if (this.inceput == 0)
                ButtonBack.setEnabled(false);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public void List1(View view) {
        inceput += cate;
        viewFlights.getFlightsPaginated(inceput, cate,token).enqueue(new Callback<List<Flight>>() {
            @Override
            public void onResponse(Call<List<Flight>> call, Response<List<Flight>> response) {
                if (response.body() != null) {
                    List<Flight> flights = response.body();
                    ArrayAdapter<Flight> arrayAdapter = new ArrayAdapter<Flight>(FlightActivity.this, android.R.layout.simple_list_item_1, flights);
                    listView.setAdapter(arrayAdapter);
                    ButtonBack.setEnabled(true);
                    if (flights.size() == 0)
                        ButtonForward.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<List<Flight>> call, Throwable t) {
                List<Flight> flights = ControllerFlight.getFlights(cate, inceput);
                ButtonBack.setEnabled(true);
                ArrayAdapter<Flight> arrayAdapter = new ArrayAdapter<Flight>(FlightActivity.this, android.R.layout.simple_list_item_1, flights);
                listView.setAdapter(arrayAdapter);
                if (flights.size() == 0)
                    ButtonForward.setEnabled(false);
            }
        });
    }

    public void List2(View view) {
        inceput -= cate;
        viewFlights.getFlightsPaginated(inceput, cate,token).enqueue(new Callback<List<Flight>>() {
            @Override
            public void onResponse(Call<List<Flight>> call, Response<List<Flight>> response) {
                if (response.body() != null) {
                    List<Flight> flights = response.body();
                    ArrayAdapter<Flight> arrayAdapter = new ArrayAdapter<Flight>(FlightActivity.this, android.R.layout.simple_list_item_1, flights);
                    listView.setAdapter(arrayAdapter);
                    ButtonForward.setEnabled(true);
                    if (inceput == 0)
                        ButtonBack.setEnabled(false);

                }
            }

            @Override
            public void onFailure(Call<List<Flight>> call, Throwable t) {
                List<Flight> flights = ControllerFlight.getFlights(cate, inceput);
                ButtonForward.setEnabled(true);
                ArrayAdapter<Flight> arrayAdapter = new ArrayAdapter<Flight>(FlightActivity.this, android.R.layout.simple_list_item_1, flights);
                listView.setAdapter(arrayAdapter);
                if (inceput == 0)
                    ButtonBack.setEnabled(false);
            }
        });
    }

    public void addFlight(View view) {
        Flight flight = new Flight(Integer.parseInt(textFieldNumber.getText().toString()), textFieldRoute.getText().toString(), false);
        viewFlights.addFlight(flight,token).enqueue(new Callback<Flight>() {
            @Override
            public void onResponse(Call<Flight> call, Response<Flight> response) {
                if (response.body() != null) {
                    Flight flight = response.body();
                    ControllerFlight.createFlight(flight);
                    inceput = -1 * cate;
                    List1(view);
                    ButtonBack.setEnabled(false);//il baga pe false
                }
            }
            @Override
            public void onFailure(Call<Flight> call, Throwable t) {
                Flight flight = new Flight(Integer.parseInt(textFieldNumber.getText().toString()), textFieldRoute.getText().toString(), false);
                ControllerFlight.createFlight(flight);
                inceput = 0;
                ButtonBack.setEnabled(false);
                List<Flight> flights = ControllerFlight.getFlights(cate, inceput);
                ArrayAdapter<Flight> arrayAdapter = new ArrayAdapter<Flight>(FlightActivity.this, android.R.layout.simple_list_item_1, flights);
                listView.setAdapter(arrayAdapter);

            }
        });
    }

    public void synchronize() throws InterruptedException {
        Thread requestThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<Flight> flights = ControllerFlight.getAllFlights();
                for (Flight flight : flights) {
                    if (flight.getAdded().equals(false)) {
                        viewFlights.addFlight(flight,token).enqueue(new Callback<Flight>() {
                            @Override
                            public void onResponse(Call<Flight> call, Response<Flight> response) {
                                if (response.body() != null) {
                                    ControllerFlight.updateFlight(flight.getFlightNumber(), true);
                                }
                            }
                            @Override
                            public void onFailure(Call<Flight> call, Throwable t) {
                            }
                        });
                    }
                }
            }
        });
        requestThread.start();
    }
    public void emailButton(View view) {
        String number = textFieldNumber.getText().toString();
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"abrudan_mada@yahoo.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Informatii");
        i.putExtra(Intent.EXTRA_TEXT, "Informatii : Zborul va avea loc in data de 19.02.2019 ");
        try {
            startActivity(Intent.createChooser(i, "Send "));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
    }
}
