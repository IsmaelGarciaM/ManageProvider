package com.ismael.manageproductsprovider;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.ismael.manageproductsprovider.adapter.InvoiceLineAdapter;
import com.ismael.manageproductsprovider.db.DatabaseContract;
import com.ismael.manageproductsprovider.interfaces.IInvoiceLinesPresenter;
import com.ismael.manageproductsprovider.interfaces.IPharmacyPresenter;
import com.ismael.manageproductsprovider.model.Invoice;
import com.ismael.manageproductsprovider.model.InvoiceLine;
import com.ismael.manageproductsprovider.model.Pharmacy;
import com.ismael.manageproductsprovider.presenter.InvoiceLinesPresenterImpl;
import com.ismael.manageproductsprovider.presenter.PharmacyPresenterImpl;
import com.ismael.manageproductsprovider.provider.ManageProductContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageInvoiceFragment extends Fragment implements IPharmacyPresenter.View, IInvoiceLinesPresenter.View{

    FloatingActionButton mAddLine;
    Button btnSave;
    TextInputEditText edtName;
    TextInputEditText edtDate;
    Spinner spnPharmacies;
    SimpleCursorAdapter scaPharmacies;
    InvoiceLineAdapter lineAdapter;
    static ManageInvoiceFragment mFragment;
    PharmacyPresenterImpl phPresenter;
    ManageInvoiceListener mCallback;
    ListView linesList;
    InvoiceLinesPresenterImpl linesPresenter;
    int idPh;

    public ManageInvoiceFragment() {
        // Required empty public constructor
    }

    public static ManageInvoiceFragment getInstance(Bundle b){

        mFragment = new ManageInvoiceFragment();
        mFragment.setArguments(b);
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lineAdapter = new InvoiceLineAdapter(getContext(), null, 1);
        phPresenter = new PharmacyPresenterImpl(this);
        linesPresenter = new InvoiceLinesPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_manage_invoice, container, false);
        mAddLine = (FloatingActionButton) rootView.findViewById(R.id.fab_add_line);
        edtName = (TextInputEditText) rootView.findViewById(R.id.nameInvoice);
        edtDate = (TextInputEditText) rootView.findViewById(R.id.dateInvoice);
        spnPharmacies = (Spinner) rootView.findViewById(R.id.spn_invoice_pharmacy);
        linesList = (ListView) rootView.findViewById(R.id.invoiceLinesList);
        btnSave = (Button) rootView.findViewById(R.id.btnSaveInvoice);


        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        String[] from = new String[]{DatabaseContract.PharmacyEntry.COLUMN_ADDRESS};
        int[] to = {android.R.id.text1};
        scaPharmacies = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, null, from, to);
        scaPharmacies.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPharmacies.setAdapter(scaPharmacies);
        final Invoice i;

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // mCallback.showInvoiceList(addInvoice());
            }
        });


        if(getArguments() != null){

            if(getArguments().getString("addline") != null){
                //linesPresenter.addLine((InvoiceLine) getArguments().getParcelable("addLine"));
            }

            final Bundle b = getArguments();
            i = b.getParcelable("INVOICE");
            edtName.setText(i.getName());
            edtDate.setText(i.getDate());
            spnPharmacies.setSelection(i.getDirPharmacy());
            linesPresenter.getInvoiceLines(lineAdapter, i);
            linesList.setAdapter(lineAdapter);

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  mCallback.showInvoiceList(updateInvoice(i));
                }
            });


        }
        else{
            edtName.setText("");
            edtDate.setText("");
        }

        spnPharmacies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                CursorWrapper cw = (CursorWrapper) parent.getItemAtPosition(position);
                idPh = cw.getInt(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mAddLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.showAddLine(null);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        phPresenter.getAllPharmacies(scaPharmacies);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mCallback = (ManageInvoiceListener) activity;
        }catch (Exception e)
        {
            throw new RuntimeException(this.getClass() + " must implements ManageInvoiceListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        scaPharmacies = null;
        mCallback = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        phPresenter = null;
        lineAdapter = null;
    }

    @Override
    public void setPharmacyCursor(Cursor cursor) {
        scaPharmacies.swapCursor(cursor);
    }

    @Override
    public void setCursorLines(Cursor cursor) {
        if(cursor != null)
            lineAdapter.swapCursor(cursor);
        else
            lineAdapter.swapCursor(null);
    }

    interface ManageInvoiceListener{
        void showInvoiceList(Bundle b);
        void showAddLine(Bundle b);
    }

    private Bundle updateInvoice(Invoice i){
        //REcogerDATOSinvoice
        String name = edtName.getText().toString();
        int idPHarmacy = idPh;
        String date = edtDate.getText().toString();
        Invoice newInvoice = new Invoice();
        newInvoice.setName(name);
        newInvoice.setIdPharmacy(idPHarmacy);
        newInvoice.setDate(date);
        newInvoice.setStatus(1);
        Bundle b = new Bundle();
        b.putString("type", "update");
        b.putParcelable("newInvoice", newInvoice);
        b.putParcelable("oldInvoice", i);
        return b;
    }

    private Bundle addInvoice(){
        String name = edtName.getText().toString();
        int idPHarmacy =  idPh;
        String date = edtDate.getText().toString();
        Invoice newInvoice = new Invoice();
        newInvoice.setName(name);
        newInvoice.setIdPharmacy(idPHarmacy);
        newInvoice.setDate(date);
        newInvoice.setStatus(1);
        Bundle b = new Bundle();
        b.putString("type", "add");
        b.putParcelable("newInvoice", newInvoice);
        return b;
    }

}
