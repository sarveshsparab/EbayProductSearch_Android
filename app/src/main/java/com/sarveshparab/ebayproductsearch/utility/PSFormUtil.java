package com.sarveshparab.ebayproductsearch.utility;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.sarveshparab.ebayproductsearch.R;
import com.sarveshparab.ebayproductsearch.pojos.PSForm;

public class PSFormUtil {

    public static void clearPSForm(View view){
        EditText psKeywordET = view.findViewById(R.id.psKeywordET);
        EditText psMilesET = view.findViewById(R.id.psMilesET);
        Spinner psCategorySpinner = view.findViewById(R.id.psCategorySpinner);
        CheckBox psNewCB = view.findViewById(R.id.psNewCB);
        CheckBox psUsedCB = view.findViewById(R.id.psUsedCB);
        CheckBox psUnspecifiedCB = view.findViewById(R.id.psUnspecifiedCB);
        CheckBox psFreeCB = view.findViewById(R.id.psFreeCB);
        CheckBox psLocalCB = view.findViewById(R.id.psLocalCB);
        CheckBox psEnableNearbyCB = view.findViewById(R.id.psEnableNearbyCB);
        TextView psKeywordErrorTV = view.findViewById(R.id.psKeywordErrorTV);
        TextView psCustLocErrorTV = view.findViewById(R.id.psCustLocErrorTV);

        psKeywordET.getText().clear();
        psMilesET.getText().clear();
        psCategorySpinner.setSelection(0);
        psNewCB.setChecked(false);
        psUsedCB.setChecked(false);
        psUnspecifiedCB.setChecked(false);
        psFreeCB.setChecked(false);
        psLocalCB.setChecked(false);
        psEnableNearbyCB.setChecked(false);
        psKeywordErrorTV.setVisibility(View.GONE);
        psCustLocErrorTV.setVisibility(View.GONE);
    }

    public static void populateCategoryTypes(Context context, View view){
        Spinner psCategorySpinner = view.findViewById(R.id.psCategorySpinner);
        ArrayAdapter<String> psCategoryAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item,
                context.getResources().getStringArray(R.array.psCategoryTypes));
        psCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        psCategorySpinner.setAdapter(psCategoryAdapter);
    }

    public static boolean isPSFormValid(View view) {
        boolean valid = true;

        EditText psKeywordET = view.findViewById(R.id.psKeywordET);
        if(psKeywordET.getText().equals("") || psKeywordET.getText().length() == 0
                || psKeywordET.getText().toString().trim().equals("")){
            valid = false;
            TextView psKeywordErrorTV = view.findViewById(R.id.psKeywordErrorTV);
            psKeywordErrorTV.setVisibility(View.VISIBLE);
        }

        CheckBox psEnableNearbyCB = view.findViewById(R.id.psEnableNearbyCB);
        if(psEnableNearbyCB.isChecked()) {

            RadioButton psCustLocRB = view.findViewById(R.id.psCustLocRB);
            if(psCustLocRB.isChecked()) {

                EditText psCustLocET = view.findViewById(R.id.psCustLocET);
                if (psCustLocET.getText().equals("") || psCustLocET.getText().length() == 0
                        || psCustLocET.getText().toString().trim().equals("")) {
                    valid = false;
                    TextView psCustLocErrorTV = view.findViewById(R.id.psCustLocErrorTV);
                    psCustLocErrorTV.setVisibility(View.VISIBLE);
                }
            }
        }

        return valid;
    }

    public static TextWatcher setPSKeywordETWatcher(final View view) {

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView psKeywordErrorTV = view.findViewById(R.id.psKeywordErrorTV);
                psKeywordErrorTV.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    TextView psKeywordErrorTV = view.findViewById(R.id.psKeywordErrorTV);
                    psKeywordErrorTV.setVisibility(View.VISIBLE);
                }
            }
        };
    }

    public static TextWatcher setPSCustLocETWatcher(final View view) {

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView psCustLocErrorTV = view.findViewById(R.id.psCustLocErrorTV);
                psCustLocErrorTV.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    TextView psCustLocErrorTV = view.findViewById(R.id.psCustLocErrorTV);
                    psCustLocErrorTV.setVisibility(View.VISIBLE);
                }
            }
        };
    }

    public static CompoundButton.OnCheckedChangeListener setNearbySearchToggler(final View view){
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                RelativeLayout psNearbySearchEnabledRL = view.findViewById(R.id.psNearbySearchEnabledRL);
                if(isChecked){
                    psNearbySearchEnabledRL.setVisibility(View.VISIBLE);
                } else {
                    psNearbySearchEnabledRL.setVisibility(View.GONE);
                }
            }
        };
    }

    public static RadioGroup.OnCheckedChangeListener setNearbyLocationTypeToggler(final View view){
        return new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                EditText psCustLocET = view.findViewById(R.id.psCustLocET);
                switch (checkedId){
                    case R.id.psCurrLocRB:
                        psCustLocET.setEnabled(false);
                        break;
                    case R.id.psCustLocRB:
                        psCustLocET.setEnabled(true);
                        break;
                }
            }
        };
    }

    public static PSForm captureFormData(View view) {
        PSForm psForm = new PSForm();

        EditText psKeywordET = view.findViewById(R.id.psKeywordET);
        Spinner psCategorySpinner = view.findViewById(R.id.psCategorySpinner);
        CheckBox psNewCB = view.findViewById(R.id.psNewCB);
        CheckBox psUsedCB = view.findViewById(R.id.psUsedCB);
        CheckBox psUnspecifiedCB = view.findViewById(R.id.psUnspecifiedCB);
        CheckBox psLocalCB = view.findViewById(R.id.psLocalCB);
        CheckBox psFreeCB = view.findViewById(R.id.psFreeCB);
        CheckBox psEnableNearbyCB = view.findViewById(R.id.psEnableNearbyCB);
        EditText psMilesET = view.findViewById(R.id.psMilesET);
        RadioButton psCurrLocRB = view.findViewById(R.id.psCurrLocRB);
        RadioButton psCustLocRB = view.findViewById(R.id.psCustLocRB);
        EditText psCustLocET = view.findViewById(R.id.psCustLocET);

        psForm.setKeyword(psKeywordET.getText().toString());
        psForm.setCategory(psCategorySpinner.getSelectedItem().toString());
        psForm.setCondNew(psNewCB.isChecked());
        psForm.setCondUsed(psUsedCB.isChecked());
        psForm.setCondUnspecified(psUnspecifiedCB.isChecked());
        psForm.setLocalPickup(psLocalCB.isChecked());
        psForm.setFreeShipping(psFreeCB.isChecked());
        psForm.setNearBySearchEnabled(psEnableNearbyCB.isChecked());
        psForm.setMiles(psMilesET.getText().toString());

        if(psCurrLocRB.isChecked()) {
            psForm.setZipCodeType(StrUtil.ZIP_TYPE_CURR);
            psForm.setCurrZipCode("");
        } else if (psCustLocRB.isChecked()) {
            psForm.setZipCodeType(StrUtil.ZIP_TYPE_CUST);
            psForm.setCustZipCode(psCustLocET.getText().toString());
        }

        return psForm;
    }
}
