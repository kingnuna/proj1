package com.example.demo.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/members")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@PostMapping("")//Member db에 저장. json반환(처리결과(result:true/false))
	public Map join(Member m) {
		Map map = new HashMap();
		boolean result = false;
		try {
			service.addMember(m);
			result = true;
		}catch(Exception e) {
			System.out.println(e);
		}
		map.put("result", result);
		return map;
	}
	
	@GetMapping("/{id}")//id로 하나 검색. json반환(처리결과(result:true/false), 검색 결과 Member객체(m))
	public Map getMember(@PathVariable("id") String id) {
		Map map = new HashMap();
		boolean result = false;
		Member m = null;
		try {
			m = service.getMember(id);
			result = true;
		}catch(Exception e) {
			System.out.println(e);
		}
		map.put("result", result);
		map.put("m", m);
		return map;
	}
	
	@PutMapping("/{id}")//수정. json반환(처리결과(result:true/false))
	public Map editMember(Member m) {
		Map map = new HashMap();
		Member mm = service.getMember(m.getId());
		mm.setPwd(m.getPwd());
		mm.setEmail(m.getEmail());
		boolean result = false;
		try {
			service.editMember(mm);
			result = true;
		}catch(Exception e) {
			System.out.println(e);
		}
		map.put("result", result);
		return map;
	}
	
	@DeleteMapping("/{id}")//삭제. json반환(처리결과(result:true/false))
	public Map delMember(@PathVariable("id") String id) {
		Map map = new HashMap();
		boolean result = false;
		try {
			service.delMember(id);
			result = true;
		}catch(Exception e) {
			System.out.println(e);
		}
		map.put("result", result);
		return map;
	}
}







