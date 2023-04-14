//Jerome Marudo Assignment 3
package org.apache.commons.mail;

import java.util.Date;

import java.util.Properties;


import javax.mail.Session;  //for getsession

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmailTest {
	//Variables used from tutorial video
	private static final String[] TEST_EMAILS = { "ab@bc.com", "a.b@c.org", 
			"abcdefghijklmnopqrst@abcdefghijklmnopqrst.com.bd" };
	private static final String[] TEST_EMPTY_EMAILS = null;
	private Object TEST_OBJECT;
	
	/* Concrete Email Class For Testing */
	private EmailConcrete email;
	
	@Before
	public void setUpEMailTest() throws Exception {
		email = new EmailConcrete();
	}
	
	@After
	public void TearDownEmailTest() throws Exception {
		
	}
	
	/*
	 *  Test addBcc(String email) function
	 */
	@Test
	public void testAddBcc() throws Exception {
		email.addBcc(TEST_EMAILS);
		assertEquals(3, email.getBccAddresses().size());
	}
	
	//Tests addBcc() when EmailException occurs
	@Test (expected = EmailException.class)
	public void testAddBccException() throws Exception{
		email.addBcc(TEST_EMPTY_EMAILS);
		assertEquals(0, email.getBccAddresses().size());
	}
	
	
	/*
	 *  Test addCc(String email) function
	 */
	@Test
	public void testAddCc() throws Exception {
		email.addCc(TEST_EMAILS);
		assertEquals(3, email.getCcAddresses().size());
	}
	
	//Tests addCc() when EmailException occurs
	@Test (expected = EmailException.class)
	public void testAddCcException() throws Exception {
		email.addCc(TEST_EMPTY_EMAILS);
		assertEquals(0, email.getBccAddresses().size());
	}
	
	
	/*
	 *  Test addHeader(String name, String value) function
	 */
	@Test
	public void testAddHeader() throws Exception {
		//this test alone makes 70% > coverage
		email.addHeader("abc123", "abc123");
		assertEquals(1, email.getHeaders().size());
	}
	
	//Tests addHeader() when IllegalArgumentException occurs when name = null
	@Test (expected = IllegalArgumentException.class)
	public void testAddHeaderNameException() throws Exception {
		email.addHeader(null, null);
		assertEquals(0, email.headers.size());
	}
	
	//Tests addHeader when IllegalArgumentException occurs when value = null
	@Test (expected = IllegalArgumentException.class)
	public void testAddHeaderValueException() throws Exception {
		email.addHeader("123abc", null);
		assertEquals(1, email.getHeaders().size());
	}
	
	
	/*
	 *  Test Email addReplyTo(String email, String name) function
	 */
	@Test
	public void TestAddReplyTo() throws Exception {
		email.addReplyTo("ab@bc.com", "abc123");
		assertEquals(1, email.getReplyToAddresses().size());
	}
	
	
	/*
	 *  Test void buildMimeMessage() function
	 */
	@Test
	public void TestBuildMimeMessage() throws Exception {
		email.setHostName("Home Name Test Example");
		email.setSubject("Test Subject");
		email.setFrom("ab@bc.com");
		email.setMsg("TestMessage");
		email.addTo("a.b@c.org");
		email.addCc(TEST_EMAILS);
		email.addBcc(TEST_EMAILS);
		email.addReplyTo("aa.b@c.org");
		email.addHeader("123abc", "123abc");
		email.setContent(email, "Test Content");
		email.updateContentType("TestContentType");
		email.buildMimeMessage();
		
		assertTrue(email.message != null);
	}
	
	//Tests buildMimeMessage() when EmailException occurs when session.getProperty() == null
	@Test (expected = EmailException.class)
	public void TestBuildMimeMessagePropertyException() throws Exception {
		email.setHostName("Home Name Test Example");
		email.buildMimeMessage();
	}
	
	//Tests buildMimeMessage() when IllegalStateException occurs when this.message == null	
	@Test (expected = IllegalStateException.class)
	public void TestBuildMimeMessageException() throws Exception {
		email.setHostName("Home Name Test Example");
		email.setFrom("ab@bc.com");
		email.addTo("a.b@c.org");
		email.buildMimeMessage();
		email.buildMimeMessage();
	}
	
	
	/*
	 *  Test String getHostName() function
	 */
	@Test
	public void TestGetHostName() throws Exception {
		String TestReturnHostName;
		TestReturnHostName = email.getHostName();
		assertTrue("Hostname = " + TestReturnHostName, TestReturnHostName == null);
		
		email.setHostName("Home Name Test Example");
		assertEquals("HostName = " + email.getHostName(),"Home Name Test Example", email.getHostName());
	}
	
	/*
	 *  Test Session getMailSession() function
	 */
	@Test
	public void TestGetMailSession() throws Exception {
		email.setSSLOnConnect(true);
		email.setHostName("Test Host Name");
		assertTrue("Mail Session does not exist", email.getMailSession() != null);
		
		Properties propsTest = new Properties();
		propsTest.put("ab@bc.com" , "a.b@c.org");
		Session sessionTest  = Session.getDefaultInstance(propsTest, null);
		email.setMailSession(sessionTest);
		assertTrue("Mail Session does not exist", email.getMailSession() != null);
	}
	
	
	/*
	 *  Test Date getSentDate() function
	 */
	@Test
	public void TestGetSentDate() throws Exception {
		assertTrue("Date was initially sent as null", email.getSentDate() != null);
		
		Date TestDate = new Date(2022, 3, 19);
		email.setSentDate(TestDate);
		assertTrue("Date was successfully sent and got: ", email.getSentDate() != null);
	}
	
	
	/*
	 *  Test int getSocketConnectionTimeout() function
	 */
	@Test
	public void TestGetSocketConnectionTimeout() throws Exception {
		assertTrue("No socket connection timeout is set", email.getSocketConnectionTimeout() != 0);
	}
	
	
	/*
	 *  Test Email setFrom(String email) function
	 */
	@Test
	public void TestSetFrom() throws Exception {
		email.setFrom(TEST_EMAILS[0]);
		assertTrue("getpersonal = " + email.getFromAddress().getPersonal(), email.getFromAddress().getPersonal() == null);
	}
}
