package TestSessionManager;

public class User {
	  /* ID                 bigint not null,
	   USER_NAM             varchar(50) not null,
	   LGIN_PWD             varchar(50) not null,
	   PWD_KEY              varchar(50) not null,
	   CERT_ORD_NO          varchar(50) not null,
	   CONCR                varchar(50),
	   MAIL_ADDR            varchar(100),
	   PHO                  varchar(50),
	   ADDR                 varchar(100),
	   REM                  varchar(500),
	   STS                  varchar(2) not null comment '0:待审批
       */
	   private Long id;
	   private String userName;
	   private String lginPwd;
	   private String pwdKey;
	   private String certOrdNo;
	   private String concr;
	   private String mailAddr;
	   private String pho;
	   private String addr;
	   private String rem;
	   private String sts;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLginPwd() {
		return lginPwd;
	}
	public void setLginPwd(String lginPwd) {
		this.lginPwd = lginPwd;
	}
	public String getPwdKey() {
		return pwdKey;
	}
	public void setPwdKey(String pwdKey) {
		this.pwdKey = pwdKey;
	}
	public String getCertOrdNo() {
		return certOrdNo;
	}
	public void setCertOrdNo(String certOrdNo) {
		this.certOrdNo = certOrdNo;
	}
	public String getConcr() {
		return concr;
	}
	public void setConcr(String concr) {
		this.concr = concr;
	}
	public String getMailAddr() {
		return mailAddr;
	}
	public void setMailAddr(String mailAddr) {
		this.mailAddr = mailAddr;
	}
	public String getPho() {
		return pho;
	}
	public void setPho(String pho) {
		this.pho = pho;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getRem() {
		return rem;
	}
	public void setRem(String rem) {
		this.rem = rem;
	}
	public String getSts() {
		return sts;
	}
	public void setSts(String sts) {
		this.sts = sts;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", lginPwd=" + lginPwd + ", pwdKey=" + pwdKey
				+ ", certOrdNo=" + certOrdNo + ", concr=" + concr + ", mailAddr=" + mailAddr + ", pho=" + pho
				+ ", addr=" + addr + ", rem=" + rem + ", sts=" + sts + "]";
	}

   

}
