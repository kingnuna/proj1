package com.example.demo.seller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/products")
public class SellerController {

	@Autowired
	private ProductService service;
	private String base_path = "C:\\shop\\";

	@PostMapping("")
	public Map addProduct(Product p) {
		Map map = new HashMap();
		boolean result = true;
		try {
			Product pp = service.addProduct(p);//추가한 제품의 번호로 이미지 파일명
			MultipartFile f = p.getFile();
			String fname = f.getOriginalFilename();// 업로드된 파일명 반환
			fname = fname.substring(fname.lastIndexOf('.'));
			File ff = new File(base_path + pp.getNum()+fname);//상품번호.jpg			
			f.transferTo(ff);
			pp.setImg_path(ff.getName());
			service.editProduct(pp);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		map.put("result", result);
		return map;
	}
	
	@GetMapping("")
	public Map getAll() {
		Map map = new HashMap();
		boolean result = true;
		ArrayList<Product> list = null;
		try {
			list = service.getAll();
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		map.put("result", result);
		map.put("list", list);
		return map;
	}
	@GetMapping("/{num}")
	public Map getProduct(@PathVariable("num") int num) {
		Map map = new HashMap();
		boolean result = true;
		Product p = null;
		try {
			p = service.getByNum(num);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		map.put("result", result);
		map.put("p", p);
		return map;
	}
	
	@GetMapping("/name/{name}")
	public Map getByName(@PathVariable("name") String name) {
		Map map = new HashMap();
		boolean result = true;
		ArrayList<Product> list = null;
		try {
			list = service.getByName(name);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		map.put("result", result);
		map.put("list", list);
		return map;
	}
	
	@PutMapping("")
	public Map editProduct(Product p) {
		Map map = new HashMap();
		Product pp = service.getByNum(p.getNum());
		pp.setName(p.getName());
		pp.setAmount(p.getAmount());
		pp.setPrice(p.getPrice());
		pp.setInfo(p.getInfo());
		boolean result = true;
		try {
			service.editProduct(pp);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		map.put("result", result);
		return map;
	}
	@DeleteMapping("/{num}")
	public Map delProduct(@PathVariable("num") int num) {
		Map map = new HashMap();
		boolean result = true;
		String fname = service.getByNum(num).getImg_path();
		try {
			service.delProduct(num);
			File f = new File(base_path+fname);
			if(f.exists()) {
				f.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		map.put("result", result);
		return map;
	}
	
	@GetMapping("/img/{img}")
	public ResponseEntity<byte[]> img(@PathVariable("img") String img_path) {
		File f = new File(base_path + img_path);
		HttpHeaders header = new HttpHeaders();
		ResponseEntity<byte[]> result = null;

		try {
			header.add("Content-Type", Files.probeContentType(f.toPath()));//이 페이지의 마임타입 지정
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(f), header, HttpStatus.OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
