package com.mamoreno.links

import scala.concurrent._
import scala.concurrent.duration._
import slick.driver.H2Driver.api._

import mappings.Actions
import mappings.Implicits._
import mappings.Mappings.Link

object Main extends App {
	def exec[A](action: DBIO[A]): A = {
    	Await.result(Actions.db.run(action), 2 seconds)
	}

	exec(Actions.createSchemaAction)
	exec(Actions.seedLinks)

	val l1 = Link("http://www.rentalia.com", "holiday rental", Set("professional", "holidays"))
	exec(Actions.addLink(l1))
	exec(Actions.findAllLinks)
	exec(Actions.descriptionContainsAction("scala"))

}	
