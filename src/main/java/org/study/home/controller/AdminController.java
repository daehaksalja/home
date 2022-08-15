package org.study.home.controller;


import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.study.home.mapper.AttachMapper;
import org.study.home.mapper.MemberMapper;

import org.study.home.model.Criteria;
import org.study.home.model.MemberDTO;
import org.study.home.model.PageDTO;
import org.study.home.model.ShipDTO;
import org.study.home.service.AdminService;
import org.study.home.service.MemberService;



@Controller
public class AdminController {
	@Autowired
	private MemberMapper mapper;

	@Autowired
	private MemberService memberService;
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private AdminService adminService;

	@Autowired
	private AttachMapper attachMapper;

	@GetMapping("/adminMenu/adminMember")
	public String adminMember(Model model) {

		List<MemberDTO> list = memberService.userList();
		model.addAttribute("list", list);

		return "board/adminMember";
	}

	@GetMapping("/adminMenu")
	public String adminMenu() {
		return "board/adminMenu";
	}

	@GetMapping("/adminMenu/goodsEnroll")
	public String goodsEnroll() {
		return "admin/goodsEnroll";
	}

	@GetMapping("/goodsRead")
	public String goodsRead(@RequestParam("shipId") String shipId, Model model) {
		ShipDTO dto = adminService.goodsRead(shipId);
		model.addAttribute("dto", dto);
		return "adminMenu/goodsRead";
	}

	@GetMapping("/goodsUpdate")
	public String goodsUpdate(@RequestParam("shipId") String shipId, Model model) {
		ShipDTO dto = adminService.goodsRead(shipId);
		model.addAttribute("dto", dto);
		return "adminMenu/goodsUpdate";
	}

	@PostMapping("/goodsUpdate")
	public String goodsUpdateProcess(ShipDTO dto) {
		adminService.goodsUpdate(dto);
		System.out.println("update ----" + dto.toString());

		return "redirect:/goodsRead?shipId=" + dto.getShipId();
	}

	@RequestMapping(value = "/adminMenu/goodsManage", method = RequestMethod.GET)
	public void goodsManage(Criteria cri, Model model) throws Exception {
		/* 상품 리스트 데이터 */

		cri.setSkip((cri.getPageNum() - 1) * 10);
		List<ShipDTO> list = adminService.goodsGetList(cri);

		if (!list.isEmpty()) {
			model.addAttribute("list", list);
		} else {
			model.addAttribute("listCheck", "empty");
			return;
		}

		/* 페이지 인터페이스 데이터 */
		model.addAttribute("pageMaker", new PageDTO(cri, adminService.goodsGetTotal(cri)));

	}

	/* 상품 등록 */
	@PostMapping("/adminMenu/goodsEnroll")
	public String goodsEnrollPOST(ShipDTO ship, RedirectAttributes rttr) {

		logger.info("goodsEnrollPOST......" + ship);

		adminService.shipEnroll(ship);

		rttr.addFlashAttribute("enroll_result", ship.getShipName());

		return "redirect:/adminMenu/goodsManage";
	}

	public AttachMapper getAttachMapper() {
		return attachMapper;
	}

	public void setAttachMapper(AttachMapper attachMapper) {
		this.attachMapper = attachMapper;
	}

	public MemberMapper getMapper() {
		return mapper;
	}

	public void setMapper(MemberMapper mapper) {
		this.mapper = mapper;
	}

	/*
	 * 첨부 파일 업로드
	 * 
	 * @SuppressWarnings("deprecation")
	 * 
	 * @PostMapping(value = "/uploadAjaxAction", produces =
	 * MediaType.APPLICATION_JSON_UTF8_VALUE) public
	 * ResponseEntity<List<AttachImageDTO>> uploadAjaxActionPOST(MultipartFile[]
	 * uploadFile) {
	 * 
	 * 이미지 파일 체크 for (MultipartFile multipartFile : uploadFile) {
	 * 
	 * File checkfile = new File(multipartFile.getOriginalFilename()); String type =
	 * null;
	 * 
	 * try { type = Files.probeContentType(checkfile.toPath());
	 * logger.info("MIME TYPE : " + type); } catch (IOException e) {
	 * e.printStackTrace(); } if (!type.startsWith("image")) { List<AttachImageDTO>
	 * list = null; return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST); } }
	 * String uploadFolder = "C:\\upload"; SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyy-MM-dd"); Date date = new Date(); String str =
	 * sdf.format(date); String datePath = str.replace("-", File.separator);
	 * 
	 * 폴더 생성 File uploadPath = new File(uploadFolder, datePath);
	 * 
	 * if (uploadPath.exists() == false) { uploadPath.mkdirs(); }
	 * 
	 * 이미저 정보 담는 객체 List<AttachImageDTO> list = new ArrayList<AttachImageDTO>(); //
	 * 향상된 for for (MultipartFile multipartFile : uploadFile) {
	 * 
	 * AttachImageDTO dto = new AttachImageDTO(); 파일 이름 String uploadFileName =
	 * multipartFile.getOriginalFilename(); dto.setFileName(uploadFileName);
	 * dto.setUploadPath(datePath); uuid 적용 파일 이름 String uuid =
	 * UUID.randomUUID().toString(); dto.setUuid(uuid);
	 * 
	 * uploadFileName = uuid + "_" + uploadFileName;
	 * 
	 * 파일 위치, 파일 이름을 합친 File 객체 File saveFile = new File(uploadPath,
	 * uploadFileName);
	 * 
	 * 파일 저장 try { multipartFile.transferTo(saveFile);
	 * 
	 * 방법 2 File thumbnailFile = new File(uploadPath, "s_" + uploadFileName);
	 * 
	 * BufferedImage bo_image = ImageIO.read(saveFile);
	 * 
	 * // 비율 double ratio = 3; // 넓이 높이 int width = (int) (bo_image.getWidth() /
	 * ratio); int height = (int) (bo_image.getHeight() / ratio);
	 * 
	 * Thumbnails.of(saveFile).size(width, height).toFile(thumbnailFile);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } list.add(dto); }
	 * 
	 * ResponseEntity<List<AttachImageDTO>> result = new
	 * ResponseEntity<List<AttachImageDTO>>(list, HttpStatus.OK); return result;
	 * 
	 * }
	 * 
	 * @GetMapping("/display") public ResponseEntity<byte[]> getImage(String
	 * fileName) { logger.info("getImage()......." + fileName);
	 * 
	 * File file = new File("C:\\upload" + fileName); ResponseEntity<byte[]> result
	 * = null;
	 * 
	 * try {
	 * 
	 * HttpHeaders header = new HttpHeaders();
	 * 
	 * header.add("Content-type", Files.probeContentType(file.toPath()));
	 * 
	 * result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header,
	 * HttpStatus.OK);
	 * 
	 * } catch (IOException e) { e.printStackTrace(); }
	 * 
	 * return result; }
	 * 
	 * 이미지 파일 삭제
	 * 
	 * @PostMapping("/deleteFile") public ResponseEntity<String> deleteFile(String
	 * fileName) {
	 * 
	 * logger.info("deleteFile........" + fileName); File file = null; try { 썸네일 파일
	 * 삭제 file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));
	 * 
	 * file.delete();
	 * 
	 * 원본 파일 삭제 String originFileName = file.getAbsolutePath().replace("s_", "");
	 * 
	 * logger.info("originFileName : " + originFileName);
	 * 
	 * file = new File(originFileName);
	 * 
	 * file.delete();
	 * 
	 * } catch (Exception e) {
	 * 
	 * e.printStackTrace();
	 * 
	 * return new ResponseEntity<String>("fail", HttpStatus.NOT_IMPLEMENTED);
	 * 
	 * } return new ResponseEntity<String>("success", HttpStatus.OK); }
	 * 
	 * public MemberMapper getMapper() { return mapper; }
	 * 
	 * public void setMapper(MemberMapper mapper) { this.mapper = mapper; }
	 * 
	 * public AttachMapper getAttachMapper() { return attachMapper; }
	 * 
	 * public void setAttachMapper(AttachMapper attachMapper) { this.attachMapper =
	 * attachMapper; }
	 * 
	 * 
	 * 이미지 정보 반환
	 * 
	 * @GetMapping(value="/getAttachList") public
	 * ResponseEntity<List<AttachImageDTO>> getAttachList(int shipId){
	 * logger.info("getAttachList.........." + shipId);
	 * 
	 * return new ResponseEntity(attachMapper.getAttachList(shipId), HttpStatus.OK);
	 * }
	 * 
	 */
}