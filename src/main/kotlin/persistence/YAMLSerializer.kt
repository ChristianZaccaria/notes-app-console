package persistence

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import models.Note
import java.io.File


class YAMLSerializer(private val file: File) : Serializer {

    @Throws(Exception::class)
    override fun read(): Any {

        var objectMapper = ObjectMapper(YAMLFactory())
        objectMapper.registerModule(KotlinModule())
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        return objectMapper
            .readerFor(object: TypeReference<List<Note>>(){})
            .readValue(File(file.toString()))
    }

    @Throws(Exception::class)
    override fun write(obj: Any?) {
        var objectMapper = ObjectMapper(YAMLFactory())
        objectMapper.writeValue(file, obj);
        }
}