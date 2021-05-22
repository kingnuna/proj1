package com.example.demo.seller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.member.Member;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository rep;
	
	public Product addProduct(Product p) {
		return rep.save(p);
	}
	
	public ArrayList<Product> getAll(){
		return (ArrayList<Product>) rep.findAll();
	}
	
	public Product getByNum(int num) {
		return rep.findById(num).orElse(null);
	}
	
	public ArrayList<Product> getByName(String name){
		name = "%"+name+"%";
		return rep.findByNameLike(name);
	}
	
	public void editProduct(Product p) {
		rep.save(p);
	}
	
	public void delProduct(int num) {
		rep.deleteById(num);
	}
	
}
