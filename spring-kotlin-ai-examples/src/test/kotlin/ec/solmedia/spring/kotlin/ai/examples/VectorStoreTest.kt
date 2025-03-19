package ec.solmedia.spring.kotlin.ai.examples

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(TestcontainersConfiguration::class)
class VectorStoreTest {

	@Autowired
	lateinit var vectorStore: VectorStore

	@ParameterizedTest
	@ValueSource(strings = ["¿Qué es la Real Audiencia de Quito?"])
	fun whenSearchingShakespeareTheme_thenRelevantPoemsReturned(theme: String) {
		val searchRequest = SearchRequest.builder()
			.query(theme)
			.topK(3)
			.build()
		val documents = vectorStore.similaritySearch(searchRequest)

		assertThat(documents)
			.hasSizeLessThanOrEqualTo(3)
			.allSatisfy { document ->
				val distance = document.metadata["distance"] as Float
				assertThat(distance).isNotNull()
			}
	}

}
