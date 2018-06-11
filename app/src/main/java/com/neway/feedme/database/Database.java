package com.neway.feedme.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.neway.feedme.model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME = "eatitDB.db";
    private static final int DB_VERSION = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public List<Order> getCarts() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        String[] selectQuery = new String[]{"ProductId", "ProductName",
                "Quantity", "Price", "Discount"};
        String sqlTable = "orderDetail";

        queryBuilder.setTables(sqlTable);
        Cursor data = queryBuilder.query(db, selectQuery, null, null,
                null, null, null);

        final ArrayList<Order> orders = new ArrayList<>();
        if (data.moveToFirst()) {
            do {

                int ProductIdIndex = data.getColumnIndex("ProductId");
                int ProductNameIndex = data.getColumnIndex("ProductName");
                int QuantityIndex = data.getColumnIndex("Quantity");
                int PriceIndex = data.getColumnIndex("Price");
                int DiscountIndex = data.getColumnIndex("Discount");

                orders.add(new Order(data.getString(ProductIdIndex), data.getString(ProductNameIndex),
                        data.getString(QuantityIndex), data.getString(PriceIndex), data.getString(DiscountIndex)));
            } while (data.moveToNext());
        }

        return orders;
    }


    public void addToChart(Order order) {
        String sqlTable = "orderDetail";

        SQLiteDatabase db = getWritableDatabase();
        String query = String.format("INSERT INTO " + sqlTable +
                "(ProductId,ProductName,Quantity,Price,Discount) VALUES ('%s','%s','%s','%s','%s');",
                order.getProductId(), order.getProductName(), order.getQuantity(), order.getPrice(),
                order.getDiscount());
        db.execSQL(query);
    }

    public void cleanChart() {
        String sqlTable = "orderDetail";

        SQLiteDatabase db = getWritableDatabase();
        String query = String.format("DELETE FROM " + sqlTable );
        db.execSQL(query);
    }

}
