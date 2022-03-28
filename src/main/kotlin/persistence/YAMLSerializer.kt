package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver
import com.thoughtworks.xstream.io.xml.DomDriver
import models.Note
import org.yaml.snakeyaml.Yaml

import java.io.*


class YAMLSerializer(private val file: File) : Serializer {

    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(JettisonMappedXmlDriver())
        xStream.allowTypes(arrayOf(Note::class.java))
        val inputStream = xStream.createObjectInputStream(FileReader(file))
        val obj = inputStream.readObject() as Any
        inputStream.close()
        return obj
    }

    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xStream = XStream(JettisonMappedXmlDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
        }
}