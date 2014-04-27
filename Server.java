
  import org.vertx.java.core.Handler;
  import org.vertx.java.core.http.HttpServerRequest;
  import org.vertx.java.platform.Verticle;
  import java.net.Socket;
  import java.io.PrintWriter;
  import java.io.InputStreamReader;
  import java.io.BufferedReader;
  import org.w3c.dom.Document;
  import org.w3c.dom.NodeList;
  import org.w3c.dom.Node;
  import org.w3c.dom.Element;
  import org.xml.sax.InputSource;
  import javax.xml.parsers.DocumentBuilderFactory;
  import javax.xml.parsers.DocumentBuilder;
  import javax.xml.parsers.ParserConfigurationException;
  import java.io.StringReader;

  public class Server extends Verticle {
      String hostName = "127.0.0.1";
      int portNumber = 81;
      Socket kkSocket = null;
      PrintWriter out = null;
      static String bidXml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?> <ad xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"smaato_ad_v0.9.xsd\" modelVersion=\"0.9\"> <imageAd> <clickUrl>http://mysite.com/landingpages/mypage/</clickUrl> <imgUrl>http://mysite.com/images/myad.jpg</imgUrl> <width>728</width> <height>90</height> <toolTip>This is a tooltip text</toolTip> <additionalText>Additional text to be displayed</additionalText> <beacons> <beacon>http://mysite.com/beacons/mybeacon1</beacon> <beacon>http://mysite.com/beacons/mybeacon2</beacon> </beacons> </imageAd> </ad>";

      public void start() {
        System.out.println("server is up");
	try {
              Socket kkSocket = new Socket(hostName, portNumber, true);
              out = new PrintWriter(kkSocket.getOutputStream());
	      BufferedReader in = new BufferedReader(
		new InputStreamReader(kkSocket.getInputStream()));
	} catch (Exception e) {
	    System.out.println("error 1: " + e.getMessage());
	}
          
        vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
              public void handle(HttpServerRequest req) {
  
  		  try {
  		      //out.write("hello\n");
  		      out.write(parseXML());
		      out.flush();
		  } catch (Exception e) {
                      System.out.println("error 2: " + portNumber + " " + e.getMessage());
 		  }
                  req.response().putHeader("Content-Length",String.valueOf("hello noam")); 
  		  req.response().end("Ok");
              }
          }).listen(9090);
      }

      public static String parseXML() {
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
     	DocumentBuilder dBuilder = null;
        Document doc = null;
     	try {
       	  dBuilder = dbFactory.newDocumentBuilder();
          InputSource is = new InputSource(new StringReader(bidXml));
          doc = dBuilder.parse(is);
          doc.getDocumentElement().getChildNodes().item(1).getChildNodes().item(5).getTextContent();
       } catch (Exception e) {
         System.out.println(e.getMessage());
       }
       return doc.getDocumentElement().getChildNodes().item(1).getTextContent();
     }

  }
                  
