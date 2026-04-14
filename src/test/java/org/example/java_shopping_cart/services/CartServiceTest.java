package org.example.java_shopping_cart.services;

import org.example.java_shopping_cart.db.ShoppingCartDAO;
import org.example.java_shopping_cart.model.CartItem;
import org.example.java_shopping_cart.model.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private ShoppingCartDAO mockDao;

    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartService = new CartService(mockConnection, mockDao);
    }

    @Test
    void saveCartCommitsTransactionWhenPersistenceSucceeds() throws SQLException {
        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(new CartItem(2, 10.0, 1));
        cart.addToCart(new CartItem(1, 5.5, 2));

        when(mockDao.saveCartRecord(2, 25.5, "en")).thenReturn(41);
        when(mockDao.saveCartItems(eq(41), anyList())).thenReturn(2);
        doNothing().when(mockConnection).commit();

        cartService.saveCart(cart, "en");

        verify(mockDao).saveCartRecord(2, 25.5, "en");
        verify(mockDao).saveCartItems(eq(41), anyList());
        verify(mockConnection).commit();
    }

    @Test
    void saveCartSkipsCommitWhenRecordInsertFails() throws SQLException {
        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(new CartItem(1, 10.0, 1));

        when(mockDao.saveCartRecord(1, 10.0, "en")).thenReturn(-1);

        cartService.saveCart(cart, "en");

        verify(mockDao).saveCartRecord(1, 10.0, "en");
        verify(mockDao, never()).saveCartItems(anyInt(), anyList());
        verify(mockConnection, never()).commit();
    }
}