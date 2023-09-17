import { useState, useEffect } from "react";

import { Col, Container, Row} from "react-bootstrap";

import {LoadAllData} from "../components/LoadAllData/LoadAllData";
import {ReadItem} from "../components/ReadItem/ReadItem";
import {CreateItemCommentary} from "../components/CreateItem/CreateItemCommentary";
import {UpdateItemCommentary} from "../components/UpdateItem/UpdateItemCommentary";


const Commentary = () => {

    const [data, setData] = useState(null);
    const [bookData, setBookData] = useState(null);


   
    const loadAllData = ()=>fetch('/api/v1/commentary')
    .then(response => response.json())
    .then(commentaryes => setData({commentaryes}))


    const loadBookId =  ()=>fetch('/api/v1/book')
    .then(response => response.json())
    .then(book => setBookData({book}))


    useEffect(() => {
        loadAllData();
        loadBookId();
    }, []);


    return (
        <Container>
        
            <Row>   
                <Col>
                    <LoadAllData data = {data} title={"все коментарии"} itemName={"commentaryes"} columnNameArray={["ID", "bookId", "message"]}  columnKeyArray = {["id", "bookId", "message"]}/>
                </Col>
                    
                <Col>
                    <Row style={{margin:5, padding:5}}>
                        <p>READ</p>
                        <ReadItem data = {data} itemName={"commentaryes"} columnNameArray={["ID", "bookId", "message"]}  columnKeyArray = {["id", "bookId", "message"]} url={"/api/v1/commentary/"}/>
                    </Row>
                    <Row style={{margin:5, padding:5}}>
                        <p>CREATE</p>
                        <CreateItemCommentary placeholder={"коментарий"} loadAllData = {loadAllData} bookData={bookData} columnNameArray={["ID", "bookId", "message"]}  columnKeyArray = {["id", "bookId", "message"]} url={"/api/v1/commentary"}/>
                    </Row>
                    <Row style={{margin:5, padding:5}}>
                        <p>UPDATE</p>
                        <UpdateItemCommentary data = {data} 
                                    placeholder={"коментарий"}
                                    loadAllData = {loadAllData} 
                                    bookData={bookData}
                                    itemName={"commentaryes"} 
                                    columnNameArray={["ID", "bookId", "message"]} 
                                    columnKeyArray = {["id", "bookId", "message"]} 
                                    url={"/api/v1/commentary"}
                                    urlSelect={"/api/v1/commentary/"}/>
                    </Row>
                </Col>
                
            </Row>
            </Container>        
            


)
}


export default Commentary