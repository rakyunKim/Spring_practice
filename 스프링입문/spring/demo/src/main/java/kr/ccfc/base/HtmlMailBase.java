package kr.ccfc.base;

public class HtmlMailBase {

    public static String BASEHTML = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\" content=\"\">\n" +
            "    <title>${title}</title> 　　\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
            "</head>\n" +
            "<body style=\"margin: 0; padding: 0;\">\n" +
            "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse;\"> 　\n" +
            "    <tr>\n" +
            "        <td>\n" +
            "            <div style=\"border: #36649d 1px dashed;margin: 30px;padding: 20px\"><label\n" +
            "                    style=\"font-size: 22px;color: #36649d;font-weight: bold\">${title}</label>\n" +
            "                <p style=\"font-size: 16px\">${content}</p>\n" +
            "            </div>\n" +
            "        </td>\n" +
            "    </tr>\n" +
            "    　\n" +
            "    <tr>\n" +
            "        <td>\n" +
            "            <p style=\"margin-left:50px;color:red;font-size: 14px \"></p>\n" +
            "        </td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td>\n" +
            "            <div align=\"right\" style=\"margin: 40px;border-top: solid 1px gray\" id=\"bottomTime\"><p\n" +
            "                    style=\"margin-right: 20px\"></p> <label style=\"margin-right: 20px\">\n" +
            "            </label>\n" +
            "            </div>\n" +
            "        </td>\n" +
            "    </tr>\n" +
            "</table>\n" +
            "</body>\n" +
            "</html>\n" +
            "\n";

}
