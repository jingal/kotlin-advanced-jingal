package com.example.effective

fun main() {
    main35()
}

// No.31
/**
 * A group of *members*.
 *
 * This class has no useful logic; it's just a documentation example.
 *
 * @param T the type of a member in this group.
 * @property name the name of this group.
 * @constructor Creates an empty group.
 */
class Group<T>(val name: String) {
    /**
     * Adds a [member] to this group.
     * @return the new size of the group.
     */
    fun add(member: T): Int {
        //...
        return 0
    }
}

// No.33
class MyLinkedList<T>(
    val head : T,
    val tail : MyLinkedList<T>?
){
    companion object{
        fun <T> of(vararg elements : T) : MyLinkedList<T>? {
            if (elements.isEmpty()) return null

            return MyLinkedList(elements[0], of(*elements.sliceArray(1 until elements.size)))
        }
    }

    override fun toString(): String {
        return if (tail != null) "$head -> $tail" else "$head"
    }
}

fun main33() {
    var list = MyLinkedList(1, MyLinkedList(2, null))
    list = MyLinkedList.of(1, 2, 3)!!

    println(list)
}

// No.35
class Html {
    private val children = mutableListOf<Tag>()

    fun body(block: Body.() -> Unit) {
        val body = Body().apply(block)
        children.add(body)
    }

    override fun toString(): String {
        return children.joinToString("\n") { it.toString() }
    }
}

abstract class Tag(val name: String) {
    protected val children = mutableListOf<Tag>()

    protected fun <T : Tag> addChild(child: T, block: T.() -> Unit): T {
        child.apply(block)
        children.add(child)
        return child
    }

    override fun toString(): String {
        return "<$name>${children.joinToString("")}</$name>"
    }
}

class Body : Tag("body") {
    fun p(block: P.() -> Unit) = addChild(P(), block)
}

class P : Tag("p") {
    operator fun String.unaryPlus() {
        // 태그의 내용을 단순 문자열로 추가
        children.add(Text(this))
    }
}

class Text(val content: String) : Tag("text") {
    override fun toString() = content
}

// DSL 시작 함수
fun html(block: Html.() -> Unit): Html {
    return Html().apply(block)
}

fun main35() {
    val document = html {
        body {
            p {
                +"Hello, Kotlin DSL!"
            }
            p {
                +"Another paragraph."
            }
        }
    }
    println(document)
}

// No.37
data class UserInfo(val name: String)
