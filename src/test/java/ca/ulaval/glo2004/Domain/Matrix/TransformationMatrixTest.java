package ca.ulaval.glo2004.Domain.Matrix;

import static org.junit.Assert.*;

import java.util.prefs.Preferences;

import ca.ulaval.glo2004.Domain.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

public class TransformationMatrixTest {
    Product product1 = new Product("product1");
    Product product2 = new Product("product2");

    @Before
    public void init() {}

    @Test(expected=ProductAlreadyThereException.class)
    public void cantItemMultipleTime() throws ProductAlreadyThereException{
        TransformationMatrix t = new TransformationMatrix();
        t.add(product1,product2,1d);
        t.add(product1,product2,2d);
    }

    @Test
    public void addProductWithoutTransformation() throws ProductAlreadyThereException{
        TransformationMatrix t = new TransformationMatrix();
        t.add(product1,product1,1d);
    }



}
