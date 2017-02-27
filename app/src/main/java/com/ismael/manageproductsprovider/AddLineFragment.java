package com.ismael.manageproductsprovider;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ismael.manageproductsprovider.adapter.ProductAdapter;
import com.ismael.manageproductsprovider.db.DatabaseContract;
import com.ismael.manageproductsprovider.interfaces.IInvoiceLinesPresenter;
import com.ismael.manageproductsprovider.model.InvoiceLine;
import com.ismael.manageproductsprovider.model.Product;
import com.ismael.manageproductsprovider.presenter.ProductPresenterImpl;
import com.ismael.manageproductsprovider.provider.ManageProductContract;


public class AddLineFragment extends Fragment implements ProductPresenterImpl.View{

    private addLineListener mListener;
    ProductPresenterImpl productPresenter;
    ProductAdapter prodAdapter;
    Spinner spnProducts;
    Button btnSave;
    EditText edtAmount;
    TextView txvPrice;
    SimpleCursorAdapter scaProducts;
    static AddLineFragment alf;

    public AddLineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_line, container, false);
        spnProducts = (Spinner) v.findViewById(R.id.spnProducts);
        edtAmount = (EditText) v.findViewById(R.id.edtAmountLine);
        txvPrice = (TextView) v.findViewById(R.id.txvPriceLine);
        return v;
    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof addLineListener) {
            mListener = (addLineListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setProductCursor(Cursor cursor) {
        scaProducts.swapCursor(cursor);
    }

    public interface addLineListener {
        void showManageInvoice(Bundle b);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] from = new String[]{DatabaseContract.ProductEntry.COLUMN_NAME};
        int[] to = {android.R.id.text1};
        scaProducts = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, null, from, to);
        scaProducts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnProducts.setAdapter(scaProducts);
        final InvoiceLine i;

        if(getArguments() != null){

            Bundle b = getArguments();
            i = b.getParcelable("INVOICELINE");

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.showManageInvoice(addLine(i));
                }
            });


        }




    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productPresenter = new ProductPresenterImpl(this);

    }
    public static AddLineFragment getInstance(Bundle b){
        if( alf == null)
            alf = new AddLineFragment();
        alf.setArguments(b);
        return alf;
    }
    @Override
    public void onStart() {
        super.onStart();
        productPresenter.getAllProducts(scaProducts);
    }

    private Bundle addLine(InvoiceLine line){
        Bundle b = new Bundle();
        Product pTmp = productPresenter.getProductByName(spnProducts.getSelectedItem().toString());
        line.setIdProduct(pTmp.getID());////////////////// getidproduct con el nombre
        line.setNameProduct(spnProducts.getSelectedItem().toString());
        line.setAmount(Integer.parseInt(edtAmount.getText().toString()));
        line.setPrice(line.getAmount() * pTmp.getPrice());
        b.putParcelable("addLine", line);
        return b;
    }
}
