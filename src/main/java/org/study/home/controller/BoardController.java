package org.study.home.controller;


import org.springframework.stereotype.Controller;
import org.study.home.service.BoardService;

@Controller
public class BoardController {

	
	private BoardService boardService;

	public BoardService getBoardService() {
		return boardService;
	}

	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
	}
	
	
}

