package com.example.michaelmsimon.finnchallengeone.Accessor;

import com.example.michaelmsimon.finnchallengeone.Model.Product;
import java.util.ArrayList;

/**
 * Created by Michael M. Simon on 2/12/2018.
 */
// this interface serves as a holder for returned data to classes that implemented it.
// It will first be called when data is received inside RestCient class
public interface OnClientApiExcuted {
    void taskCompleted(ArrayList<Product> result);
}
