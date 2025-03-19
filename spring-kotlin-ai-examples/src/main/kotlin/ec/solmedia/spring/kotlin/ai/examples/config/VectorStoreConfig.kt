package ec.solmedia.spring.kotlin.ai.examples.config

import org.springframework.ai.reader.tika.TikaDocumentReader
import org.springframework.ai.transformer.splitter.TokenTextSplitter
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class VectorStoreConfig(private val vectorStore: VectorStore): ApplicationRunner {

    @Value("classpath:/Ecuador.pdf")
    private lateinit var pdfResource: Resource

    override fun run(args: ApplicationArguments?) {
        val documentReader = TikaDocumentReader(pdfResource)
        val splitter = TokenTextSplitter()
        vectorStore.accept(splitter.apply(documentReader.get()))
    }
}