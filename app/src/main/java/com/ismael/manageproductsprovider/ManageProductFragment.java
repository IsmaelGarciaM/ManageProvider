package com.ismael.manageproductsprovider;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import java.util.Locale;

import com.ismael.manageproductsprovider.db.DatabaseContract;
import com.ismael.manageproductsprovider.interfaces.ICategoryPresenter;
import com.ismael.manageproductsprovider.interfaces.IProduct;
import com.ismael.manageproductsprovider.model.Product;
import com.ismael.manageproductsprovider.presenter.CategoryPresenterImpl;
import com.ismael.manageproductsprovider.provider.ManageProductContract;
import com.squareup.picasso.Picasso;

/**
 * Class which adds a product to our list
 * @author José Antonio Barranquero Fernández
 * @version 1.0*/

public class ManageProductFragment extends Fragment implements ICategoryPresenter.View, IProduct {
    private EditText mEdtName, mEdtDesc, mEdtBrand, mEdtDosage, mEdtStock, mEdtPrice;
    private Button mBtnAddMed;
    private ImageView mImgMedicine;
    private ManageProductListener mCallback;
    //private ManagePresenterImpl mPresenter;
    private View parent;
    private MultiListProductFragment.ListProductListener list;
    private Spinner spnCategories;
    private SimpleCursorAdapter sca;
    private CategoryPresenterImpl categoryPresenter;
    int idCategory;
    static ManageProductFragment fragment;

    /*@Override
    public void showMessage(String message) {
        Snackbar.make(parent, getString(Integer.parseInt(message)), Snackbar.LENGTH_SHORT);
    }*/

    @Override
    public void setCursorCategory(Cursor cursor) {
        if(cursor != null)
            sca.swapCursor(cursor);
    }

    public interface ManageProductListener {
        void showListProduct(Bundle b);
    }

    public static ManageProductFragment getInstance(Bundle bundle) {
        fragment = new ManageProductFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mPresenter = new ManagePresenterImpl();
        categoryPresenter = new CategoryPresenterImpl(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] from = new String[]{DatabaseContract.CategoryEntry.COLUMN_NAME};
        int[] to = {android.R.id.text1};
        sca = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, null, from, to);
        sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategories.setAdapter(sca);
        spnCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = ((SimpleCursorAdapter)spnCategories.getAdapter()).getCursor();
                c.moveToPosition(position);
                idCategory = c.getInt(c.getColumnIndex(ManageProductContract.Category._ID));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        //categoryPresenter.getAllCategories(sca);
        categoryPresenter.getAllCategories(sca);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        categoryPresenter = null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (ManageProductListener)activity;
        } catch (ClassCastException ex) {
            throw new ClassCastException(ex.getMessage() + " activity must implement ManageProductListener interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
        sca = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_manage_product, container, false);

        parent = rootView.findViewById(R.id.activity_add_product);
        spnCategories = (Spinner)rootView.findViewById(R.id.categorySpin);
        mEdtName = (EditText)rootView.findViewById(R.id.edtName);
        mEdtDesc = (EditText)rootView. findViewById(R.id.edtDesc);
        mEdtBrand = (EditText) rootView.findViewById(R.id.edtBrand);
        mEdtDosage = (EditText) rootView.findViewById(R.id.edtDosage);
        mEdtStock = (EditText) rootView.findViewById(R.id.edtStock);
        mEdtPrice = (EditText) rootView.findViewById(R.id.edtPrice);
        mImgMedicine = (ImageView) rootView.findViewById(R.id.imgMedicine);
        mImgMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, 1);
                }
            }
        });

        mBtnAddMed = (Button) rootView.findViewById(R.id.btnAddMed);


        final Product product;
        ImageView image = new ImageView(getContext());

        if (getArguments() != null) {
            product = getArguments().getParcelable(IProduct.PRODUCT_KEY);
            mEdtName.setText(product.getName());
            mEdtDesc.setText(product.getDescription());
            mEdtBrand.setText(product.getBrand());
            mEdtDosage.setText(product.getDosage());
            mEdtStock.setText(product.getStock());
            mEdtPrice.setText(String.format(Locale.US, Double.toString(product.getPrice())));
            Picasso.with(getContext()).load(product.getImage()).into(mImgMedicine);
            mBtnAddMed.setText(getResources().getString(R.string.edit_product));


            mBtnAddMed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.showListProduct(updateProduct(product));
                }
            });
        } else {
            mBtnAddMed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.showListProduct(saveProduct());
                }
            });
        }
        return rootView;
    }

    private Bundle saveProduct() {
        Product product = new Product();
        product.setName(mEdtName.getText().toString());
        product.setDescription(mEdtDesc.getText().toString());
        product.setBrand(mEdtBrand.getText().toString());
        product.setDosage(mEdtDosage.getText().toString());
        product.setPrice(Double.parseDouble(mEdtPrice.getText().toString()));
        product.setStock(mEdtStock.getText().toString());
        product.setImage("http://st.depositphotos.com/1000674/4382/v/950/depositphotos_43829575-stock-illustration-box-with-pills.jpg");
        product.setIdCategory(idCategory);

        Bundle b = new Bundle();
        b.putString("action", PRODUCT_KEY);
        b.putParcelable(PRODUCT_KEY, product);
        return b;

        /*if(addAction){
           //mPresenter.saveProduct(product);
        }
        else {
           //mPresenter.updateProduct(product);
        }*/
    }

    private Bundle updateProduct(Product oldProduct) {
        Product newProduct = new Product();
        newProduct.setName(mEdtName.getText().toString());
        newProduct.setDescription(mEdtDesc.getText().toString());
        newProduct.setBrand(mEdtBrand.getText().toString());
        newProduct.setDosage(mEdtDosage.getText().toString());
        newProduct.setPrice(Double.parseDouble(mEdtPrice.getText().toString()));
        newProduct.setStock(mEdtStock.getText().toString());
        newProduct.setImage("http://st.depositphotos.com/1000674/4382/v/950/depositphotos_43829575-stock-illustration-box-with-pills.jpg");


        newProduct.setIdCategory(idCategory);

        Bundle b = new Bundle();
        b.putString("action", OLD_KEY);
        b.putParcelable(OLD_KEY, oldProduct);
        b.putParcelable(PRODUCT_KEY, newProduct);
        return b;


        //mPresenter.updateProduct(product, product1);
    }
}
