package com.example.maxcruz.data

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * Rule to mock a service API
 */
class MockWebServerRule : TestRule {

    private val server = MockWebServer()

    val url: String
        get() = server.url("/").toString()

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {

            @Throws(Throwable::class)
            override fun evaluate() {
                server.start()
                base.evaluate()
                server.shutdown()
            }
        }
    }

    /**
     * Put the body content in the next response
     */
    fun enqueueResponse(body: String) {
        val response = MockResponse()
        response.setBody(body)
        server.enqueue(response)
    }

    /**
     * Put an not found error in the next response
     */
    fun enqueueNotFound() {
        val response = MockResponse()
        response.setResponseCode(404)
        server.enqueue(response)
    }
}
