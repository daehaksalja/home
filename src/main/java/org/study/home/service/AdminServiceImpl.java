package org.study.home.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.home.mapper.AdminMapper;
import org.study.home.model.Criteria;
import org.study.home.model.ShipDTO;

@Service

public class AdminServiceImpl implements AdminService{
	@Autowired
	private AdminMapper adminMapper;
	/* 상품 등록 */

	@Override
	public void shipEnroll(ShipDTO ship) {
		System.out.println("shipEnroll");
		
		adminMapper.shipEnroll(ship);
		
		if(ship.getImageList() == null || ship.getImageList().size() <= 0) {
			return;
		}
		 //람다식 활용한 for문
		ship.getImageList().forEach(attach ->{
			attach.setShipId(ship.getShipId());
			adminMapper.imageEnroll(attach);
		});
		
		
	}
	
	@Override
	public ShipDTO goodsRead(String shipId) {
		return adminMapper.goodsRead(shipId);
	}

	@Override
	public void goodsUpdate(ShipDTO dto) {
		adminMapper.goodsUpdate(dto);

	}

	
	
	
	
	/* 상품 리스트 */
	   @Override
	   public List<ShipDTO> goodsGetList(Criteria cri) {
	      System.out.println("goodsGetTotalList().........." + cri);
	      return adminMapper.goodsGetList(cri);
	   }

	   /* 상품 총 갯수 */
	   public int goodsGetTotal(Criteria cri) {
	      System.out.println("goodsGetTotal()........." + cri);
	      return adminMapper.goodsGetTotal(cri);
	   }
}
