// MainActivity.java
// Calculates bills using 15% and custom percentage tips.
/**
 * Author:zhanglan 张岚
 * Student ID:12301055
 * Date:2015-3-10
 * Content:Enhanced Tip Calculator App
 */
package com.deitel.tipcalculator;

import java.text.NumberFormat; // for currency formatting

import android.app.Activity; // base class for activities
import android.os.Bundle; // for saving state information
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; // EditText listener
import android.widget.EditText; // for bill amount input
import android.widget.SeekBar; // for changing custom tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener; // SeekBar listener
import android.widget.TextView; // for displaying text

// MainActivity class for the Tip Calculator app
public class MainActivity extends Activity {
    // currency and percent formatters
    private static final NumberFormat currencyFormat = NumberFormat
            .getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat
            .getPercentInstance();

    private double billAmount = 0.0; // bill amount entered by the user
    private double customPercent = 0.18; // initial custom tip percentage
    private int people = 1;
    private TextView amountDisplayTextView; // shows formatted bill amount
    private TextView percentCustomTextView; // shows custom tip percentage
    private TextView tip15TextView; // shows 15% tip
    private TextView total15TextView; // shows total with 15% tip
    private TextView tipCustomTextView; // shows custom tip amount
    private TextView totalCustomTextView; // shows total with custom tip
    private TextView splitTextView; //
    private TextView splitTextView2; //

    // called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // call superclass's version
        setContentView(R.layout.activity_main); // inflate the GUI

        // get references to the TextViews
        // that MainActivity interacts with programmatically
        amountDisplayTextView = (TextView) findViewById(R.id.amountDisplayTextView);
        percentCustomTextView = (TextView) findViewById(R.id.percentCustomTextView);
        tip15TextView = (TextView) findViewById(R.id.tip15TextView);
        total15TextView = (TextView) findViewById(R.id.total15TextView);
        tipCustomTextView = (TextView) findViewById(R.id.tipCustomTextView);
        totalCustomTextView = (TextView) findViewById(R.id.totalCustomTextView);
        splitTextView = (TextView) findViewById(R.id.splitTextView);
        splitTextView2 = (TextView) findViewById(R.id.splitTextView2);

        // update GUI based on billAmount and customPercent
        amountDisplayTextView.setText(currencyFormat.format(billAmount));
        updateStandard(); // update the 15% tip TextViews
        updateCustom(); // update the custom tip TextViews

        // set amountEditText's TextWatcher
        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        final EditText ev = (EditText) findViewById(R.id.EditText);

        ev.setText(String.valueOf(people));
        ev.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                try {
                    people = Integer.parseInt(s.toString());
                } catch (NumberFormatException e) {
                    people = 1;
                }
                updateCustomSplit();
                updateStandardSplit();
            }

        });

        // set customTipSeekBar's OnSeekBarChangeListener
        SeekBar customTipSeekBar = (SeekBar) findViewById(R.id.customTipSeekBar);
        customTipSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
    } // end method onCreate

    // updates 15% tip TextViews
    private void updateStandard() {
        // calculate 15% tip and total
        double fifteenPercentTip = billAmount * 0.15;
        double fifteenPercentTotal = billAmount + fifteenPercentTip;

        // display 15% tip and total formatted as currency
        tip15TextView.setText(currencyFormat.format(fifteenPercentTip));
        total15TextView.setText(currencyFormat.format(fifteenPercentTotal));
        updateStandardSplit();
    } // end method updateStandard

    // updates the custom tip and total TextViews
    private void updateCustom() {
        // show customPercent in percentCustomTextView formatted as %
        percentCustomTextView.setText(percentFormat.format(customPercent));

        // calculate the custom tip and total
        double customTip = billAmount * customPercent;
        double customTotal = billAmount + customTip;

        // display custom tip and total formatted as currency
        tipCustomTextView.setText(currencyFormat.format(customTip));
        totalCustomTextView.setText(currencyFormat.format(customTotal));
        updateCustomSplit();
    } // end method updateCustom

    private void updateCustomSplit() {
        // calculate the custom tip and total
        double customTip = billAmount * customPercent;
        double customTotal = billAmount + customTip;

        double split = customTotal / people;
        if(((int)(customTotal*100))%people==0)
            splitTextView.setText(currencyFormat.format(split));
        else
            splitTextView.setText("can not split");
    }

    private void updateStandardSplit() {
        // calculate 15% tip and total
        double fifteenPercentTip = billAmount * 0.15;
        double fifteenPercentTotal = billAmount + fifteenPercentTip;
        double split = fifteenPercentTotal / people;
        if(((int)(fifteenPercentTotal*100))%people==0)
            splitTextView2.setText(currencyFormat.format(split));
        else
            splitTextView2.setText("can not split");
    }

    // called when the user changes the position of SeekBar
    private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener() {
        // update customPercent, then call updateCustom
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // sets customPercent to position of the SeekBar's thumb
            customPercent = progress / 100.0;
            updateCustom(); // update the custom tip TextViews
        } // end method onProgressChanged

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        } // end method onStartTrackingTouch

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        } // end method onStopTrackingTouch
    }; // end OnSeekBarChangeListener

    // event-handling object that responds to amountEditText's events
    private TextWatcher amountEditTextWatcher = new TextWatcher() {
        // called when the user enters a number
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // convert amountEditText's text to a double
            try {
                billAmount = Double.parseDouble(s.toString()) / 100.0;
            } // end try
            catch (NumberFormatException e) {
                billAmount = 0.0; // default if an exception occurs
            } // end catch

            // display currency formatted bill amount
            amountDisplayTextView.setText(currencyFormat.format(billAmount));
            updateStandard(); // update the 15% tip TextViews
            updateCustom(); // update the custom tip TextViews
        } // end method onTextChanged

        @Override
        public void afterTextChanged(Editable s) {
        } // end method afterTextChanged

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        } // end method beforeTextChanged
    }; // end amountEditTextWatcher
} // end class MainActivity

/*************************************************************************
 * (C) Copyright 1992-2014 by Deitel & Associates, Inc. and * Pearson Education,
 * Inc. All Rights Reserved. * * DISCLAIMER: The authors and publisher of this
 * book have used their * best efforts in preparing the book. These efforts
 * include the * development, research, and testing of the theories and programs
 * * to determine their effectiveness. The authors and publisher make * no
 * warranty of any kind, expressed or implied, with regard to these * programs
 * or to the documentation contained in these books. The authors * and publisher
 * shall not be liable in any event for incidental or * consequential damages in
 * connection with, or arising out of, the * furnishing, performance, or use of
 * these programs. *
 *************************************************************************/
