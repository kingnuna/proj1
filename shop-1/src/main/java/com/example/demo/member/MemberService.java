package com.example.demo.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository rep;
	
	public void addMember(Member m) {
		rep.save(m);
	}
	
	public Member getMember(String id) {
		return rep.findById(id).orElse(null);
	}
	
	public void editMember(Member m) {
		rep.save(m);
	}
	
	public void delMember(String id) {
		rep.deleteById(id);
	}
}
