import { useState, useEffect } from "react";

import { Col, Container, Row} from "react-bootstrap";
import {LoadAllData} from "../components/LoadAllData/LoadAllData";
import {ReadItem} from "../components/ReadItem/ReadItem";
import {CreateItem} from "../components/CreateItem/CreateItem";
import {UpdateItem} from "../components/UpdateItem/UpdateItem";


const Author = () => {


    const [data, setData] = useState(null);

   
    const loadAllData = ()=>fetch('/api/v1/author')
    .then(response => response.json())
    .then(authors => setData({authors}))

    useEffect(() => {
        loadAllData();
    }, []);

    

    console.log("----", data)
    return (
        <Container>
        
            <Row>   
                <Col>
                    <LoadAllData data = {data} title={"все авторы"} itemName={"authors"} columnNameArray={["ID", "name"]}  columnKeyArray = {["id", "name"]}/>
                </Col>
                    
                <Col>
                    <Row style={{margin:5, padding:5}}>
                        <p>READ</p>
                        <ReadItem data = {data} itemName={"authors"} columnNameArray={["ID", "name"]}  columnKeyArray = {["id", "name"]} url={"/api/v1/author/"}/>
                    </Row>
                    <Row style={{margin:5, padding:5}}>
                        <p>CREATE</p>
                        <CreateItem placeholder={"автор"} loadAllData = {loadAllData} columnNameArray={["ID", "name"]}  columnKeyArray = {["id", "name"]} url={"/api/v1/author"}/>
                    </Row>
                    <Row style={{margin:5, padding:5}}>
                        <p>UPDATE</p>
                        <UpdateItem data = {data} 
                                    placeholder={"автор"}
                                    loadAllData = {loadAllData} 
                                    itemName={"authors"} 
                                    columnNameArray={["ID", "name"]} 
                                    columnKeyArray = {["id", "name"]} 
                                    url={"/api/v1/author"}
                                    urlSelect={"/api/v1/author/"}/>
                    </Row>
                </Col>
                
            </Row>
            </Container>        
            


)
}

export default Author