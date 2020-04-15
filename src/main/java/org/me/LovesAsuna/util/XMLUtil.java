package org.me.LovesAsuna.util;

import org.me.LovesAsuna.data.BotData;
import org.me.LovesAsuna.enumeration.PathType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class XMLUtil {
    public static void read() {
        Path path = Paths.get(PathType.FILES_PATHS.getPath()).resolveSibling("config.xml");
        File file = path.toFile();
        if (!file.exists()) {
            try {
                FileSystem system = FileSystems.newFileSystem(Paths.get(BotData.getFilePath()), null);

                Files.copy(system.getPath("config.xml"), path.resolveSibling("config.xml"));
                Files.copy(system.getPath("config.dtd"), path.resolveSibling("config.dtd"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            Node groups = doc.getDocumentElement();
            NodeList group = groups.getChildNodes();
            List<Long> groupList = new ArrayList<>();
            for (int i = 0; i < group.getLength(); i++) {
                Node groupID = group.item(i);
                groupList.add(Long.parseLong(groupID.getTextContent()));
            }
            BotData.setGroups(groupList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
