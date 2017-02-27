package com.ismael.manageproductsprovider;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ismael.manageproductsprovider.adapter.InvoiceAdapter;
import com.ismael.manageproductsprovider.interfaces.IInvoicePresenter;
import com.ismael.manageproductsprovider.model.Invoice;
import com.ismael.manageproductsprovider.presenter.InvoicePresenterImpl;
import com.ismael.manageproductsprovider.provider.ManageProductContract;


public class InvoiceListFragment extends Fragment implements IInvoicePresenter.View {

    FloatingActionButton mAdd;
    private InvoiceAdapter mAdapter;
    private InvoicePresenterImpl mPresenter;
    ListView listInvoice;
    static InvoiceListFragment invoiceFr;
    private InvoiceListListener mCallback;

    public InvoiceListFragment() {
        // Required empty public constructor
    }

    public static InvoiceListFragment getInstance(Bundle b){
        if(invoiceFr== null) {
            invoiceFr = new InvoiceListFragment();
            invoiceFr.setArguments(b);
        }
        return invoiceFr;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new InvoiceAdapter(getContext(), null, 1);
        mPresenter = new InvoicePresenterImpl(this, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_invoice_list, container, false);
        mAdd = (FloatingActionButton) rootView.findViewById(R.id.fab_add_sale);
        listInvoice = (ListView) rootView.findViewById(R.id.listInvoice);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listInvoice.setAdapter(mAdapter);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.showManageInvoice(null);
            }
        });

        listInvoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("INVOICE", (Invoice) parent.getItemAtPosition(position));
                mCallback.showManageInvoice(bundle);
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (InvoiceListListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Una vez que la vista ha sido creada:
        mPresenter.getInvoices(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter = null;
    }

    @Override
    public void setInvoiceCursor(Cursor cursor) {
        if (cursor != null)
            mAdapter.swapCursor(cursor);
    }

    interface InvoiceListListener{
        void showManageInvoice(Bundle invoice);
    }
}