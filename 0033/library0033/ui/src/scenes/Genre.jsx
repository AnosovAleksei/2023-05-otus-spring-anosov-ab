import { useState, useEffect } from "react";

import { Col, Container, Row} from "react-bootstrap";

import {LoadAllData} from "../components/LoadAllData/LoadAllData";
import {ReadItem} from "../components/ReadItem/ReadItem";
import {CreateItem} from "../components/CreateItem/CreateItem";
import {UpdateItem} from "../components/UpdateItem/UpdateItem";


const Genre = () => {

    const [data, setData] = useState(null);

   
    const loadAllData = ()=>fetch('/api/v1/genre')
    .then(response => response.json())
    .then(genries => setData({genries}))

    useEffect(() => {
        loadAllData();
    }, []);



    console.log("----", data)
    return (
        <Container>
        
            <Row>   
                <Col>
                    <LoadAllData data = {data} title={"все жанры"} itemName={"genries"} columnNameArray={["ID", "name"]}  columnKeyArray = {["id", "name"]}/>
                </Col>
                    
                <Col>
                    <Row style={{margin:5, padding:5}}>
                        <p>READ</p>
                        <ReadItem data = {data} itemName={"genries"} columnNameArray={["ID", "name"]}  columnKeyArray = {["id", "name"]} url={"/api/v1/genre/"}/>
                    </Row>
                    <Row style={{margin:5, padding:5}}>
                        <p>CREATE</p>
                        <CreateItem placeholder={"жанр"} loadAllData = {loadAllData} columnNameArray={["ID", "name"]}  columnKeyArray = {["id", "name"]} url={"/api/v1/genre"}/>
                    </Row>
                    <Row style={{margin:5, padding:5}}>
                        <p>UPDATE</p>
                        <UpdateItem data = {data} 
                                    placeholder={"жанр"}
                                    loadAllData = {loadAllData} 
                                    itemName={"genries"} 
                                    columnNameArray={["ID", "name"]} 
                                    columnKeyArray = {["id", "name"]} 
                                    url={"/api/v1/genre"}
                                    urlSelect={"/api/v1/genre/"}/>
                    </Row>
                </Col>
                
            </Row>
            </Container>        
            


)
}

export default Genre