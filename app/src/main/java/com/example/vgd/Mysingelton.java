package com.example.vgd;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by For on 4/24/2017.
 */
public class Mysingelton {
    private static Mysingelton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;
    private Mysingelton(Context Context)
    {
        mCtx = Context;
        requestQueue = getRequestQueue();
    }

    public static  synchronized Mysingelton getInstance (Context context)
    {
        if (mInstance==null)
        {
            mInstance =new Mysingelton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {

        if (requestQueue==null)
        {
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;

    }

    public<T> void addTorequestque(Request<T> request)
    {
        requestQueue.add(request);
    }
}
