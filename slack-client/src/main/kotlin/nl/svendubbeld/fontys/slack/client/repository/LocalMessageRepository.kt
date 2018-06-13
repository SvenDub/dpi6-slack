package nl.svendubbeld.fontys.slack.client.repository

import nl.svendubbeld.fontys.slack.client.LocalMessage
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LocalMessageRepository : CrudRepository<LocalMessage, Int?>
