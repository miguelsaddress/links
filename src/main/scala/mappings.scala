package com.mamoreno.links.mappings

import java.sql.Timestamp
import java.time.LocalDateTime
import java.net.URL

import slick.driver.H2Driver.api._

import ColumnDataMapper._
import Mappings._
import Implicits._

object Implicits {
    implicit def stringToURL(str: String) = new URL(str)
}

object Mappings {
    case class Link(
        url: URL,
        description: String = "",
        tags: Set[String] = Set[String](),
        createdAt: LocalDateTime = LocalDateTime.now(),
        updatedAt: LocalDateTime = LocalDateTime.now(),
        id: Long = 0L)


    class LinkTable(tag: Tag) extends Table[Link](tag, "links") {
        def url         = column[URL]("url")(urlColumnType)
        def description = column[String]("description")
        def tags        = column[Set[String]]("tags")(setStringColumnType)
        def createdAt   = column[LocalDateTime]("created_at")(localDateTimeColumnType)
        def updatedAt   = column[LocalDateTime]("updated_at")(localDateTimeColumnType)
        def id          = column[Long]("id", O.PrimaryKey, O.AutoInc)

        def * = (url, description, tags, createdAt, updatedAt, id) <> (Link.tupled, Link.unapply)
    }

    lazy val Links = TableQuery[LinkTable]
}

object ColumnDataMapper {

    implicit val urlColumnType = MappedColumnType.base[URL, String](
        url => url.toString(),
        str => new URL(str)
    )

    implicit val localDateTimeColumnType = MappedColumnType.base[LocalDateTime, Timestamp](
        ldt => Timestamp.valueOf(ldt),
        t => t.toLocalDateTime
    )

    implicit val setStringColumnType = MappedColumnType.base[Set[String], String](
        tags => tags.mkString(", "),
        tagsString => tagsString.split(", ").toSet
    )

}

object Actions {
    val db = Database.forConfig("linksdb")

    def createSchemaAction = Links.schema.create
    def findAllLinks = Links.result
    def seedLinks = Links ++= Seq(
        Link("http://www.mamoreno.com", "my website", Set("linkedin", "personal", "profile")),
        Link("http://www.scala-lang.org", "scala lang website", Set("scala", "learning", "official")),
        Link("http://www.lightbend.com", "lightbend website scala makers", Set("scala", "official"))
    )
    def addLink(link: Link) = Links += link
    def descriptionContainsAction(needle: String) = Links.filter(_.description like s"%$needle%").result
}