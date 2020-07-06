
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Map {
	private int _size;
	private int _counter = 0;
	private int[][] _map;

	// size = מספר השורות לקריאה בקובץ
	// sizeW =מספר האזורים בכל שורה בקובץ
	public Map(int size, int sizeW, String fileName) {
		// size*sizeW אתחול מטריצה בגודל
		_map = new int[size][sizeW];
		_size = sizeW;

		try {
			File file = new File(fileName);

			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			Document doc = docBuilder.parse(file);

			if (doc.hasChildNodes()) {
				readNode(doc.getChildNodes());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public int[][] get_map() {
		return _map;
	}

	private void readNode(NodeList nodeList) {
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);

			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				if (tempNode.hasAttributes()) {
					NamedNodeMap nodeMap = tempNode.getAttributes();

					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);
						_map[_counter / _size][_counter % _size] = Integer.parseInt(node.getNodeValue());
						_counter++;
					}
				}

				if (tempNode.hasChildNodes()) {
					readNode(tempNode.getChildNodes());
				}
			}
		}
	}
}
