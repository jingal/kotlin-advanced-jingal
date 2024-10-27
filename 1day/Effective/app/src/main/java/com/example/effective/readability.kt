package com.example.effective

fun main() {
    main15()
}

// No.15
class Node(val name: String) {
    fun makeChild(childName: String) =
        create("$name.$childName")
            .also { print("Created ${name}") }

    fun create(name: String): Node? = Node(name)
}

fun main15() {
    val node = Node("parent")
    node.makeChild("child")
}

@DslMarker
annotation class HtmlDsl

@HtmlDsl
class Table {
    fun tr(block: Tr.() -> Unit) {
        Tr().apply(block)
    }
}

@HtmlDsl
class Tr {
    fun td(content: String) {
        println("td: $content")
    }
}

fun table(block: Table.() -> Unit) {
    Table().apply(block)
}

fun main_15() {
    table {
        tr {
            td("Column 1")

            // 이제 잘못된 스코프에서 tr을 호출하려고 하면 컴파일 오류가 발생합니다.
            // tr { td("Value 1") }  // 오류 발생
        }
    }
}