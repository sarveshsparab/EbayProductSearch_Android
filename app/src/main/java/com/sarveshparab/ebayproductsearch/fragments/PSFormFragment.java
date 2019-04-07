package com.sarveshparab.ebayproductsearch.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sarveshparab.ebayproductsearch.R;
import com.sarveshparab.ebayproductsearch.pojos.PSForm;
import com.sarveshparab.ebayproductsearch.utility.PSFormUtil;

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
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create a view
        final View view = inflater.inflate(R.layout.fragment_psform, container, false);

        PSFormUtil.populateCategoryTypes(this.getContext(), view);

        EditText psKeywordET = view.findViewById(R.id.psKeywordET);
        psKeywordET.addTextChangedListener(PSFormUtil.setPSKeywordETWatcher(view));

        EditText psCustLocET = view.findViewById(R.id.psCustLocET);
        psCustLocET.addTextChangedListener(PSFormUtil.setPSCustLocETWatcher(view));

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

                    Log.d("xxxxxx",psForm.toString());
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
}
