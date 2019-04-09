package com.sarveshparab.ebayproductsearch.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sarveshparab.ebayproductsearch.R;
import com.sarveshparab.ebayproductsearch.activities.SearchResultsActivity;
import com.sarveshparab.ebayproductsearch.adapters.ZipAutoAdapter;
import com.sarveshparab.ebayproductsearch.network.CallArbitrator;
import com.sarveshparab.ebayproductsearch.network.NetworkCall;
import com.sarveshparab.ebayproductsearch.network.NetworkCallBack;
import com.sarveshparab.ebayproductsearch.pojos.PSForm;
import com.sarveshparab.ebayproductsearch.utility.PSFormUtil;
import com.sarveshparab.ebayproductsearch.utility.StrUtil;
import com.sarveshparab.ebayproductsearch.utility.ValUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PSFormFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PSFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PSFormFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private JSONObject currZipCode;
    private Handler zipAutoCompleteHandler;

    public PSFormFragment() {
        // Required empty public constructor
    }

    public static PSFormFragment newInstance() {
        PSFormFragment fragment = new PSFormFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fetchCurrLocation(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create a view
        final View view = inflater.inflate(R.layout.fragment_psform, container, false);

        PSFormUtil.populateCategoryTypes(this.getContext(), view);

        // ZipCode Auto-complete logic STARTS ******************************************************

        final AppCompatAutoCompleteTextView zipAutoCompleteTV = view.findViewById(R.id.psCustLocET);
        final ZipAutoAdapter zipAutoAdapter = new ZipAutoAdapter(getContext(),
                android.R.layout.simple_dropdown_item_1line);
        zipAutoCompleteTV.setThreshold(ValUtil.ZIP_THRESHOLD);
        zipAutoCompleteTV.setAdapter(zipAutoAdapter);

        zipAutoCompleteTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        zipAutoCompleteTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                zipAutoCompleteHandler.removeMessages(ValUtil.INITIATE_ZIP_AUTOCOMPLETE);
                zipAutoCompleteHandler.sendEmptyMessageDelayed(ValUtil.INITIATE_ZIP_AUTOCOMPLETE,
                        ValUtil.ZIP_AUTOCOMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        zipAutoCompleteHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == ValUtil.INITIATE_ZIP_AUTOCOMPLETE) {
                    if (!TextUtils.isEmpty(zipAutoCompleteTV.getText())) {
                        NetworkCall.fetchAutoCompleteZip(zipAutoAdapter,
                                getActivity().getApplicationContext(),
                                new HashMap<String, String>() {{
                                    put(StrUtil.ZIP_AUTO_QP_KEY, zipAutoCompleteTV.getText().toString());
                                }});
                    }
                }
                return false;
            }
        });

        // ZipCode Auto-complete logic ENDS ********************************************************

        EditText psKeywordET = view.findViewById(R.id.psKeywordET);
        psKeywordET.addTextChangedListener(PSFormUtil.setPSKeywordETWatcher(view));

//        EditText psCustLocET = view.findViewById(R.id.psCustLocET);
//        psCustLocET.addTextChangedListener(PSFormUtil.setPSCustLocETWatcher(view));

        CheckBox psNearbySearchEnableCB = view.findViewById(R.id.psEnableNearbyCB);
        psNearbySearchEnableCB.setOnCheckedChangeListener(PSFormUtil.setNearbySearchToggler(view));

        RadioGroup psLocRG = view.findViewById(R.id.psLocRG);
        psLocRG.setOnCheckedChangeListener(PSFormUtil.setNearbyLocationTypeToggler(view));

        Button psClearBtn = view.findViewById(R.id.psClearBtn);
        psClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PSFormUtil.clearPSForm(view);
            }
        });

        Button psSearchBtn = view.findViewById(R.id.psSearchBtn);
        psSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PSFormUtil.isPSFormValid(view)){
                    PSForm psForm = PSFormUtil.captureFormData(view);

                    try {
                        psForm.setCurrZipCode(currZipCode==null ? "" :
                                currZipCode.getString(StrUtil.JSON_RESPONSE_MESSAGE_KEY));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.v(StrUtil.LOG_TAG+"|PSForm",psForm.toString());
                    Log.v(StrUtil.LOG_TAG+"|Forward","To SearchResults Activity");

                    Intent searchResultsActivity = new Intent(getActivity(), SearchResultsActivity.class);
                    searchResultsActivity.putExtra(StrUtil.PSFORM_PARCEL, psForm);
                    startActivity(searchResultsActivity);
                } else{
                    Toast.makeText(v.getContext(),"Please fix all fields with errors",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void fetchCurrLocation(Context actCtx) {
        CallArbitrator.makeRequest(StrUtil.IP_API_URL, new NetworkCallBack() {
            @Override
            public void onSuccess(JSONObject result) throws JSONException {
                JSONObject jsonObject = new JSONObject()
                        .put(StrUtil.JSON_RESPONSE_STATUS_KEY, StrUtil.JSON_SUCCESS_RESPONSE)
                        .put(StrUtil.JSON_RESPONSE_MESSAGE_KEY, result.getString(StrUtil.IP_API_ZIP_KEY));

                currZipCode = jsonObject;
            }

            @Override
            public void onError(String result) throws Exception {
                Log.v(StrUtil.LOG_TAG+"|IP-API_Error", "Call failed with error : "+result);
                JSONObject jsonObject = new JSONObject()
                        .put(StrUtil.JSON_RESPONSE_STATUS_KEY, StrUtil.JSON_ERROR_RESPONSE)
                        .put(StrUtil.JSON_RESPONSE_MESSAGE_KEY, "IP-API call failed");

                currZipCode = jsonObject;
            }
        }, actCtx, null);
    }

}
