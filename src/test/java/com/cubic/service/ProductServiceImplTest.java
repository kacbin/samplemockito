package com.cubic.service;

import static org.junit.Assert.*;

import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Spy;

import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import java.sql.SQLException;

import com.cubic.testutil.ProductTestData;
import com.shashi.dao.ProductDAO;
import com.shashi.dao.ProductDetailDAO;
import com.shashi.entity.ProductEntity;
import com.shashi.service.ProductServiceImpl;
import com.shashi.service.exception.ProductException;
import com.shashi.vo.ProductVO;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

	@Mock
	private ProductDAO productDao;
	@Mock
	private ProductDetailDAO detailDao;

	@Spy
	private ProductServiceImpl impl;

	// private ProductServiceImpl impl = new ProductServiceImpl();
	private ProductVO input = null;
	private ProductEntity output = null;

	@Before
	public void setUp() {
		input = ProductTestData.createProductVO();
		output = ProductTestData.createProductEntity();
		when(impl.getDao()).thenReturn(productDao);
		when(impl.getDetailDao()).thenReturn(detailDao);
		// impl.setDao(productDao);
		// impl.setDetailDao(detailDao);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveProductNullProductVO() {
		impl.saveProduct(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveProductNullProductName() {
		input.setProductName(null);
		impl.saveProduct(input);
	}

	@Test
	public void testCreateProduct() throws SQLException {
		// ProductDAO productDao = mock(ProductDAO.class);
		// ProductDetailDAO detailDao = mock(ProductDetailDAO.class);

		when(productDao.createProduct(any(ProductEntity.class))).thenReturn(output);
		// impl.setDao(productDao);
		// impl.setDetailDao(detailDao);

		ProductVO result = impl.saveProduct(input);
		assertProductInfo(result);

		InOrder inorder = inOrder(productDao, detailDao);

		inorder.verify(productDao, times(1)).createProduct(any(ProductEntity.class));
		inorder.verify(detailDao, times(1)).saveProductDetail(any(ProductEntity.class));

		verify(productDao, never()).find(any(Long.class));
		verify(productDao, never()).updateProduct(any(ProductEntity.class));

	}

	private void assertProductInfo(ProductVO result) {
		assertNotNull(result);
		assertNotNull(result.getId());
		assertEquals(input.getProductName(), result.getProductName());
		assertEquals(input.getDescription(), result.getDescription());
	}

	@Test
	public void testSaveProduct() throws SQLException {
		// ProductDAO productDao = mock(ProductDAO.class);
		// ProductDetailDAO detailDao = mock(ProductDetailDAO.class);

		when(productDao.createProduct(any(ProductEntity.class))).thenReturn(output);
		// impl.setDao(productDao);
		// impl.setDetailDao(detailDao);

		ProductVO result = impl.saveProduct(input);
		assertProductInfo(result);

		verify(productDao, times(1)).createProduct(any(ProductEntity.class));
		verify(detailDao, times(1)).saveProductDetail(any(ProductEntity.class));

		verify(productDao, never()).find(any(Long.class));
		verify(productDao, never()).updateProduct(any(ProductEntity.class));

	}

	@Test
	public void testUpateProduct() throws SQLException {
		input.setId(new Long(2000));

		when(productDao.find(any(Long.class))).thenReturn(output);

		ProductVO result = impl.saveProduct(input);
		assertProductInfo(result);

		InOrder inorder = inOrder(productDao, detailDao);

		inorder.verify(productDao).find(any(Long.class));
		inorder.verify(productDao).updateProduct(any(ProductEntity.class));
		inorder.verify(detailDao).saveProductDetail(any(ProductEntity.class));

		verify(productDao, never()).createProduct(any(ProductEntity.class));

	}

	// @Test(expected = ProductException.class)
	// public void testUpateProductException() throws SQLException {
	// input.setId(new Long(2000));
	//
	// when(productDao.find(any(Long.class))).thenThrow(new SQLException("The
	// connection to DB failed"));
	//
	// impl.saveProduct(input);
	//
	// verify(productDao).find(any(Long.class));
	//
	// // following lines not needed though
	// verifyNeverMethod();
	//
	// }

	private void verifyNeverMethod() throws SQLException {
		verify(productDao, never()).updateProduct(any(ProductEntity.class));
		verify(detailDao, never()).saveProductDetail(any(ProductEntity.class));
		verify(productDao, never()).createProduct(any(ProductEntity.class));
	}
}
