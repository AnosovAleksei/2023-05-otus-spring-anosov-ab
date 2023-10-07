import { useState, useEffect } from "react";

import { Col, Container, Row} from "react-bootstrap";
import {LoadAllData} from "../components/LoadAllData/LoadAllData";
import {ReadItem} from "../components/ReadItem/ReadItem";
import {CreateItemBook} from "../components/CreateItem/CreateItemBook";
import {UpdateItemBook} from "../components/UpdateItem/UpdateItemBook";
import {DeleteItem} from "../components/DeleteItem/DeleteItem";

const Book = () => {
    const [data, setData] = useState(null);

    const [authorData, setAuthorData] = useState(null);
    const [genreData, setGenreData] = useState(null);
   
   
    const loadAllData = ()=>fetch('/api/v1/book')
    .then(response => response.json())
    .then(books => setData({books}))

    const loadAuthor =  ()=>fetch('/api/v1/author')
    .then(response => response.json())
    .then(author => setAuthorData({author}))

    const loadGenre =  ()=>fetch('/api/v1/genre')
    .then(response => response.json())
    .then(genre => setGenreData({genre}))

    useEffect(() => {
        loadAllData();
        loadAuthor();
        loadGenre();
    }, []);

    

    return (
        <Container>
        
            <Row>   
                <Col>
                    <LoadAllData data = {data} title={"все авторы"} itemName={"books"} columnNameArray={["ID", "name", "authorName", "genreName"]}  columnKeyArray = {["id", "name", "authorName", "genreName"]}/>
                </Col>
                    
                <Col>
                    <Row style={{margin:5, padding:5}}>
                        <p>READ</p>
                        <ReadItem data = {data} itemName={"books"} columnNameArray={["ID", "name", "authorName", "genreName"]}  columnKeyArray = {["id", "name", "authorName", "genreName"]} url={"/api/v1/book/"}/>
                    </Row>
                    <Row style={{margin:5, padding:5}}>
                        <p>CREATE</p>
                        <CreateItemBook placeholder={"название"} 
                                        loadAllData = {loadAllData} 
                                        authorData={authorData}
                                        genreData={genreData}
                                        columnNameArray={["ID", "name", "authorName", "genreName"]}  
                                        columnKeyArray = {["id", "name", "authorName", "genreName"]} 
                                        url={"/api/v1/book"}/>
                    </Row>
                    <Row style={{margin:5, padding:5}}>
                        <p>UPDATE</p>
                        <UpdateItemBook data = {data} 
                                    placeholder={"название"}
                                    loadAllData = {loadAllData} 
                                    authorData={authorData}
                                    genreData={genreData}
                                    itemName={"books"} 
                                    columnNameArray={["ID", "name", "authorName", "genreName"]} 
                                    columnKeyArray = {["id", "name", "authorName", "genreName"]} 
                                    url={"/api/v1/book"}
                                    urlSelect={"/api/v1/book/"}/>
                    </Row>
                    <Row style={{margin:5, padding:5}}>
                        <p>DELETE</p>
                        <DeleteItem data = {data} 
                                    itemName={"books"} 
                                    loadAllData = {loadAllData} 
                                    columnNameArray={["ID", "name", "authorName", "genreName"]}  
                                    columnKeyArray = {["id", "name", "authorName", "genreName"]} 
                                    url={"/api/v1/book/"}/>
                    </Row>
                </Col>
                
            </Row>
            </Container>        
            


)
    
}

export default Book