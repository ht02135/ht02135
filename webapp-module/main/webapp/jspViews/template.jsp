<%@ taglib prefix="tile" uri="http://tiles.apache.org/tags-tiles"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title><tile:insertAttribute name="title" ignore="true" /></title>
    </head>
    <body>
        <table border="1" cellpadding="2" cellspacing="2" align="center">
            <tr>
                <td height="30" colspan="2">
                    <tile:insertAttribute name="header" />
                </td>
            </tr>
            <tr>
                <td height="250">
                    <tile:insertAttribute name="menu" />
                </td>
                <td width="350">
                    <tile:insertAttribute name="content" />
                </td>
            </tr>
            <tr>
                <td height="30" colspan="2">
                    <tile:insertAttribute name="footer" />
                </td>
            </tr>
        </table>
    </body>
</html>
