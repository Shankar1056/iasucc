package com.tutorialsbuzz.navigationdrawer.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.payUMoney.sdk.SdkConstants;
import com.payUMoney.sdk.SdkSession;
import com.ucc.application.R;
import com.tutorialsbuzz.navigationdrawer.activity.common.ClsGeneral;
import com.tutorialsbuzz.navigationdrawer.activity.common.CustomRequest;
import com.tutorialsbuzz.navigationdrawer.activity.common.Utilz;
import com.tutorialsbuzz.navigationdrawer.activity.fragment.AllThreeFragment;
import com.tutorialsbuzz.navigationdrawer.activity.model.TestSeriesModel;
import com.tutorialsbuzz.navigationdrawer.activity.webservices.WebServices;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by indglobal on 28/1/16.
 */
public class PracticePaper extends AppCompatActivity {

    TextView _name,rating_text,download,buynow;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    ArrayList<TestSeriesModel> testSerieslist,testreviewlist,reviewDetails;
    TextView reviews,publisher;
    TestSeriesModel testSeriesModel;
    ImageView back;
    RatingBar vendor_rating;
    String email;
    int position;
    String fullname,mail;
    private String TAG = "Practice Paper";
    private ProgressBar mProgressBar;
    private String user_id;
    String mgs,status;

    HashMap<String, String> params = new HashMap<>();
    public final int RESULT_FAILED = 90;
    public final int RESULT_BACK = 8;

    //download file
    String dest_file_path = "test.pdf";
    int downloadedSize = 0, totalsize;
    String download_file_url = "http://fzs.sve-mo.ba/sites/default/files/dokumenti-vijesti/sample.pdf";
    float per = 0;
    TextView tv_loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        tv_loading = new TextView(this);
        setContentView(R.layout.practice_paper);
        tv_loading.setGravity(Gravity.CENTER);
        tv_loading.setTypeface(null, Typeface.BOLD);

        _name=(TextView)findViewById(R.id._name);
        back=(ImageView)findViewById(R.id.back);
        download=(TextView)findViewById(R.id.download);
        buynow=(TextView)findViewById(R.id.buynow);
        vendor_rating=(RatingBar)findViewById(R.id.vendor_rating);
        rating_text=(TextView)findViewById(R.id.rating_text);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.container,new AllThreeFragment()).commit();
        reviews=(TextView)findViewById(R.id.reviews);
        publisher=(TextView) findViewById(R.id.publisher);
        testSerieslist=(ArrayList<TestSeriesModel>) getIntent().getSerializableExtra("testSerieslist");
        testreviewlist=(ArrayList<TestSeriesModel>)getIntent().getSerializableExtra("testreviewlist");
        reviewDetails=new ArrayList<TestSeriesModel>();
        position=getIntent().getExtras().getInt("position");

       fullname= ClsGeneral.getPreferences(PracticePaper.this, "fullname");
        mail=ClsGeneral.getPreferences(PracticePaper.this, "email");
        user_id=ClsGeneral.getPreferences(PracticePaper.this, "user_id");

if (testreviewlist.size()>0) {
    for (int i = 0; i < testreviewlist.size(); i++) {
        if (testSerieslist.get(position).getId().equalsIgnoreCase(testreviewlist.get(i).getId())) {
            testSeriesModel = testreviewlist.get(i);
            reviewDetails.add(testSeriesModel);
        }
    }

}
        if (testSerieslist.size()>0) {
            _name.setText(testSerieslist.get(position).getName());
        }

if (reviewDetails.size()>0){
    rating_text.setText( String.valueOf(Float.parseFloat(reviewDetails.get(0).getRating())));

    vendor_rating.setRating(Integer.parseInt(reviewDetails.get(0).getRating()));
    reviews.setText(reviewDetails.size() + " " + getResources().getString(R.string.reviewsno));
}
        publisher.setText(testSerieslist.get(position).getPublisher());
        email= ClsGeneral.getPreferences(PracticePaper.this, "email");



        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((testSerieslist.size() > 0) && (reviewDetails.size()>0)) {
                    Intent intent = new Intent(PracticePaper.this, PracticePaperReviews.class);
                    intent.putExtra("testSeriesId", testSerieslist.get(position).getId());
                    intent.putExtra("testSeriesName", testSerieslist.get(position).getName());
                    intent.putExtra("testSeriesBy", testSerieslist.get(position).getPublisher());
                    intent.putExtra("rating_text", reviewDetails.get(position).getRating());
                    intent.putExtra("publisher", testSerieslist.get(position).getPublisher());

                    intent.putExtra("reviewDetails", reviewDetails);
                    startActivity(intent);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadAndOpenPDF();
                //calltwoapis();
            }
        });




/*
        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                new GetAllMenuList().execute(ApiHandler.TEST_SERIES);
            }
        });
*/


       /* if (SdkConstants.WALLET_SDK) {
            findViewById(R.id.history).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.history).setVisibility(View.GONE);
        }*/
    }

    private void downloadAndOpenPDF() {
            new Thread(new Runnable() {
                public void run() {
                    Uri path = Uri.fromFile(downloadFile(download_file_url));
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(path, "application/pdf");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } catch (ActivityNotFoundException e) {
//                        tv_loading
//                                .setError("PDF Reader application is not installed in your device");
                        Toast.makeText(PracticePaper.this, "PDF Reader application is not installed in your device", Toast.LENGTH_SHORT).show();
                    }
                }
            }).start();


    }

    private File downloadFile(String dwnload_file_path) {

            File file = null;
            try {

                URL url = new URL(dwnload_file_path);
                HttpURLConnection urlConnection = (HttpURLConnection) url
                        .openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);

                // connect
                urlConnection.connect();

                // set the path where we want to save the file
                File SDCardRoot = Environment.getExternalStorageDirectory();
                // create a new file, to save the downloaded file
                file = new File(SDCardRoot, dest_file_path);

                FileOutputStream fileOutput = new FileOutputStream(file);

                // Stream used for reading the data from the internet
                InputStream inputStream = urlConnection.getInputStream();

                // this is the total size of the file which we are
                // downloading
                totalsize = urlConnection.getContentLength();
                setText("Starting PDF download...");

                // create a buffer...
                byte[] buffer = new byte[1024 * 1024];
                int bufferLength = 0;

                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    fileOutput.write(buffer, 0, bufferLength);
                    downloadedSize += bufferLength;
                    per = ((float) downloadedSize / totalsize) * 100;
                    setText("Total PDF File size  : "
                            + (totalsize / 1024)
                            + " KB\n\nDownloading PDF " + (int) per
                            + "% complete");
                }
                // close the output stream when complete //
                fileOutput.close();
                setText("Download Complete. Open PDF Application installed in the device.");

            } catch (final MalformedURLException e) {
                setTextError("Some error occured. Press back and try again.",
                        Color.RED);
            } catch (final IOException e) {
                setTextError("Some error occured. Press back and try again.",
                        Color.RED);
            } catch (final Exception e) {
                setTextError(
                        "faiiled to download image. Please check your internet connection.",
                        Color.RED);
            }
            return file;
        }

        void setTextError(final String message, final int color) {
            runOnUiThread(new Runnable() {
                public void run() {
                    tv_loading.setTextColor(color);
                    tv_loading.setText(message);
                }
            });

        }

        void setText(final String txt) {
            runOnUiThread(new Runnable() {
                public void run() {
                    tv_loading.setText(txt);
                }
            });


    }

    public void makePayment(View view) {

            if (SdkSession.getInstance(this) == null) {
                SdkSession.startPaymentProcess(this, params);
            } else {
                SdkSession.createNewInstance(this);

            String hashSequence = "YjnBWm" + "|" + "0nf7" + "|" + testSerieslist.get(position).getPrice() + "|" +testSerieslist.get(position).getName() + "|" + fullname + "|"
                    + mail + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "0xCeFcnS";
            params.put("key", "YjnBWm");
            params.put("MerchantId", "5359266");
            /*String hashSequence = "mdyCKV" + "|" + "0nf7" + "|" + amt.getText().toString() + "|" + "product_name" + "|" + "piyush" + "|"
                    + "piyush.jain@payu.in" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "Je7q3652";
            params.put(SdkConstants.KEY, "mdyCKV");
            params.put(SdkConstants.MERCHANT_ID, "4914106");*/
            String hash = hashCal(hashSequence);
            Log.i("hash", hash);
            params.put(SdkConstants.TXNID, "0nf7");// debug
            params.put("SURL", "https://mobiletest.payumoney.com/mobileapp/payumoney/success.php");
            params.put("FURL", "https://mobiletest.payumoney.com/mobileapp/payumoney/failure.php");
            params.put(SdkConstants.PRODUCT_INFO, testSerieslist.get(position).getName());
            params.put(SdkConstants.FIRSTNAME, fullname);
            params.put(SdkConstants.EMAIL, mail);
            params.put(SdkConstants.PHONE, ClsGeneral.getPreferences(PracticePaper.this, "phone"));
            params.put(SdkConstants.AMOUNT, testSerieslist.get(position).getPrice());
            params.put("hash", hash);
            params.put("udf1", "");
            params.put("udf2", "");
            params.put("udf3", "");
            params.put("udf4", "");
            params.put("udf5", "");
            SdkSession.startPaymentProcess(this, params);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //if(data!=null) {
        if (requestCode == SdkSession.PAYMENT_SUCCESS) {
            if (resultCode == RESULT_OK) {
                Log.i("app_activity", "success");

                if (data != null && data.hasExtra(SdkConstants.IS_HISTORY_CALL)) {
                    return;
                } else {
                    //Log.i(SdkConstants.PAYMENT_ID, data.getStringExtra(SdkConstants.PAYMENT_ID));


                    fetchNewsData(data.getStringExtra(SdkConstants.PAYMENT_ID),testSerieslist.get(position).getId(),"1","success");

                }
                // finish();
            }

            if (resultCode == RESULT_CANCELED) {
                Log.i("app_activity", "failure");


                customdialog("Canceled", SdkConstants.PAYMENT_ID);

                //Write your code if there's no result
            }

            if (resultCode == RESULT_FAILED) {
                Log.i("app_activity", "failure");

                if (data != null) {
                    if (data.getStringExtra(SdkConstants.RESULT).equals("cancel")) {

                    } else {

                            String a=data.getStringExtra(SdkConstants.PAYMENT_ID);
                        fetchNewsData(data.getStringExtra(SdkConstants.PAYMENT_ID), testSerieslist.get(position).getId(), "0","failure");
                    }
                }
                //Write your code if there's no result
            }

            if (resultCode == RESULT_BACK) {
                Log.i("app_activity", "User returned without login");


                Toast.makeText(getApplicationContext(), "User returned without login", Toast.LENGTH_LONG).show();
                /*Intent intent = new Intent(this, paymentSuccess.class);
                intent.putExtra(SdkConstants.RESULT, "cancelled");
                startActivity(intent);*/

                //Write your code if there's no result
            }
        }
        //}
    }

    private void customdialog(String canceled, String paymentId) {


        final Dialog dialog = new Dialog(PracticePaper.this);
        dialog.setContentView(R.layout.success);
        dialog.setTitle("UCC...");

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.status);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        if (canceled.equalsIgnoreCase("Canceled")) {

            image.setImageResource(R.drawable.faiiled);
            text.setText("Payment failed!");
        }
        if (canceled.equalsIgnoreCase("failure")) {
            text.setText("Payment failed!"+"\n"+"Your PaymentId : "+paymentId);
            image.setImageResource(R.drawable.faiiled);

        }
        if (canceled.equalsIgnoreCase("Success")) {
            text.setText("Congratz!!Your payment is successful"+"\n"+"Your PaymentId : "+paymentId);
            image.setImageResource(R.drawable.succesfull);

        }



        Button dialogButton = (Button) dialog.findViewById(R.id.clickbutton);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }


    private void fetchNewsData(final String stringExtra, String id, String s, final String stats) {
final String statss=stats;
            mProgressBar.setVisibility(View.VISIBLE);
            final RequestQueue requestQueue = Volley.newRequestQueue(this);
            Map<String, String> params = new HashMap<String, String>();
            params.put("option","payment");
            params.put("test_series_id",id);
            params.put("userid",user_id);
            params.put("transaction_status",s);
            params.put("order_id",stringExtra);
            params.put("amount",testSerieslist.get(position).getPrice());
            params.put("book",testSerieslist.get(position).getName());
            params.put("author",testSerieslist.get(position).getPublisher());
            final CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, WebServices.NEWS_IMAGE_TITLE, params,
                    new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response.toString());
                            try {
                                if (response.length()>0){
                                    mProgressBar.setVisibility(View.GONE);
                                    if (response.has("mgs")){
                                         status=response.getString("status");
                                    }

                                    if (response.has("mgs")){
                                         mgs=response.getString("mgs");
                                    }

                                    if (status.equalsIgnoreCase("true")){

                                        if (stats.equalsIgnoreCase("failure")){
                                            customdialog("failure",stringExtra);
                                        }
                                        if (stats.equalsIgnoreCase("success")){
                                            customdialog("Success",stringExtra);
                                        }



                                        Toast.makeText(getApplicationContext(), mgs, Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), mgs, Toast.LENGTH_LONG).show();
                                    }


                                } else {
                                    Log.e("ServiceHandler", "Couldn't get any data from the url");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Server Error: " + error.getMessage());

                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            // Adding request to request queue
            requestQueue.add(jsObjRequest);




    }


    public static String hashCal(String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hexString.toString();
    }

    class GetAllMenuList extends AsyncTask<String, Void, String> {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String result1;
        String selectedLocation;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressDialog.show(PracticePaper.this, "Loading data...",
                    "");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {


            nameValuePairs.add(new BasicNameValuePair("option", "3"));
            nameValuePairs.add(new BasicNameValuePair("key",email));
            nameValuePairs.add(new BasicNameValuePair("useremail", email));
            nameValuePairs.add(new BasicNameValuePair("test_series_id",testSerieslist.get(position).getId()));

            try {

                result1 = Utilz.executeHttpPost(params[0], nameValuePairs);
//                result1="{\"employee\":[{\"name\":\"Land\"},{\"name\":\"Building\"}]}";


            } catch (Exception e) {
                e.printStackTrace();
            }

            return result1;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            if (progressDialog != null) {
                progressDialog.dismiss();
            }



            try {



                Log.d("Response: ", "> " + result);
                if (result != null) {
                    if (testSerieslist.size()>0){
                        testSerieslist.clear();
                    }
                    if (testreviewlist.size()>0){
                        testreviewlist.clear();
                    }

                    JSONObject object = new JSONObject(result);

                    String  status = object.getString("status");
                    if (status.equalsIgnoreCase("true")) {

                        Toast.makeText(PracticePaper.this,R.string.bought_testpaper,Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(PracticePaper.this, "", Toast.LENGTH_LONG).show();
                    }

                } else {
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}

