package com.ismael.manageproductsprovider.interfaces;

/*
 * Copyright (c) 2017 José Luis del Pino Gallardo.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  jose.gallardo994@gmail.com
 */

import android.database.Cursor;
import android.widget.CursorAdapter;

import com.ismael.manageproductsprovider.model.Invoice;
import com.ismael.manageproductsprovider.provider.ManageProductContract;

/**
 * Created by joselu on 13/02/17.
 */

public interface IInvoicePresenter {
    void getInvoices(CursorAdapter adapter);

    void addInvoice(Invoice invoice);

    void updateInvoice(Invoice oldINvoice, Invoice newInvoice);

    void deleteInvoice(Invoice invoice);

    interface View {
        void setInvoiceCursor(Cursor cursor);
    }
}
