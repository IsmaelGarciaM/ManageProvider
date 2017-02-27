package com.ismael.manageproductsprovider;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ismael.manageproductsprovider.adapter.PharmacyAdapter;
import com.ismael.manageproductsprovider.interfaces.IPharmacyPresenter;
import com.ismael.manageproductsprovider.presenter.PharmacyPresenterImpl;

/**
 * A simple {@link Fragment} subclass.
 */
public class PharmacyListFragment extends Fragment implements IPharmacyPresenter.View{

    private static PharmacyPresenterImpl presenter;
    public static String CHEMIST_KEY = "CHEMIST";
    PharmacyAdapter pharmacyAdapter;
    private ListPharmacyListener mCallback;
    ListView listaPh;
    private static PharmacyListFragment clf;


    public static PharmacyListFragment getInstance(Bundle args){

        if(clf == null){
            clf = new PharmacyListFragment();
        clf.setArguments(args);}
        return  clf;
    }

    @Override
    public void setPharmacyCursor(Cursor cursor) {
        if(cursor != null) pharmacyAdapter.swapCursor(cursor);
    }

    public interface ListPharmacyListener{
        void showListPharmacies();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PharmacyPresenterImpl(this);
    }

    public PharmacyListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rV = inflater.inflate(R.layout.fragment_chemist_list, container, false);;
        listaPh = (ListView) rV.findViewById(R.id.Chemistlist);
        pharmacyAdapter = new PharmacyAdapter(getContext(), null, 1);
        return rV;

    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.getAllPharmacies(pharmacyAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (ListPharmacyListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.getMessage() + " activity must implement ListPharmacyListener interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pharmacyAdapter = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaPh.setAdapter(pharmacyAdapter);

    }
}
