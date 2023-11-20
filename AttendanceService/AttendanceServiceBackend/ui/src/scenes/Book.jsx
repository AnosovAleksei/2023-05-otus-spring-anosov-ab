import { useState, useEffect } from "react";

import { Col, Container, Row} from "react-bootstrap";
import {LoadAllData} from "../components/LoadAllData/LoadAllData";
import {ReadItem} from "../components/ReadItem/ReadItem";
import Select from "react-select";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

const Book = () => {
    const [data, setData] = useState(null);
    const [controlDate, setControlDate] = useState(null);
    const [students, setStudents] = useState(null);
    const [teamId, setTeamId] = useState(null);
    const [checkedStudent, setCheckedStudent] = useState(null);
    const [controlLastDate, setControlLastDate] = useState(null);

   
   
    const loadAllData = ()=>fetch('/api/v1/team')
    .then(response => response.json())
    .then(team => setData({team}))

    const loadStudents = (teamId)=>fetch(`/api/v1/${teamId}/student`)
    .then(response => response.json())
    .then(students => setStudents({students}))

    const loadLastDate = (teamId)=>fetch(`/api/v1/${teamId}/control-write-date`)
    .then(response => response.text())
    .then(controlLastDate => setControlLastDate({controlLastDate}))


    useEffect(() => {
        loadAllData();

    }, []);

    let options = null;
    if(!!data && !!data["team"]){
        options = data["team"].map((item) => ({['value']: item.id, ['label']: item.description}));
        console.log(options);
    }

    let controlDateOptions = null;
    if(!!controlDate && !!controlDate["controlDate"]){
        controlDateOptions = controlDate["controlDate"].map((item) => ({['value']: item, ['label']: item}));
    }

    const onSelectStusents = (data) => {
        console.log(teamId);
        fetch(`/api/v1/${teamId}/student/${data}`)
        .then(response => response.json())
        .then(students => (setStudents({students})))
       };

    useEffect(() => {
        if(!!students){
            let temp = {};
            students['students'].map((item) => (temp[item.id]= item.status));
            setCheckedStudent(temp);
        }
    }, [students]);

    const onSelect = (id) => {
        setTeamId(id)
        console.log('-->', id);
        fetch(`/api/v1/${id}/control-date`)
        .then(res => {
            if(res.ok) {
                setControlDate(null);
                return res.json()
            }
            loadStudents(id);
            loadLastDate(id);
          })



        // .then(response => response.json())
        .then(controlDate => setControlDate({controlDate}))
        // .catch(err => {
        //     console.log('caught it!',err);
        //  });
       };


    const onSaveData = (data)=> {
        console.log("<->", checkedStudent);
        const temp = Array.from(new Map(Object.entries(checkedStudent)), ([name, value]) => (
                { 'id': name, 'status': value}
        )
        );
        fetch(`/api/v1/${teamId}/student`, 
                {
                    method: "POST", // *GET, POST, PUT, DELETE, etc.
                    mode: "cors", // no-cors, *cors, same-origin
                    cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
                    credentials: "same-origin", // include, *same-origin, omit
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    redirect: "follow", // manual, *follow, error
                    referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
                    body: JSON.stringify(temp), // body data type must match "Content-Type" header
                }
            
            
            )
            .then(response => {
                console.log("status send data : ", response);
                
            })
    }

    const checkboxChange = (data)=> {
        let temp = {...checkedStudent};
        temp[data.target.id] = data.target.checked
        setCheckedStudent(temp);
    }

    console.log("--------->", data, controlDate, students,checkedStudent);

    return (
        <Container>
        
            <Row  style={{margin:5, padding:5}}>   
                <Col>
                    {!!options &&
                    <Select options={options}
                            placeholder = {"доступные классы"}
                            onChange={opt => onSelect(opt.value)}
                    />}
                </Col>
                <Col>
                    {!!controlDateOptions &&
                    <Select options={controlDateOptions}
                            placeholder = {"доступные даты"}
                            onChange={opt => onSelectStusents(opt.value)}
                    />}
                </Col>
                    
                
                
            </Row>
            <Row style={{margin:5, padding:5}}>
            {!!students && !!controlDateOptions &&
                    <Form>
                        {students['students'].map((item) => (
                            <div key={`default-checkbox-th`} className="mb-3">
                                
                                
                                
                                <Form.Check type={'checkbox'} id={`teacher-${item.id}`}>
                                    <Form.Check.Label>{item.surname}</Form.Check.Label>
                                    <Form.Check.Input type={'checkbox'} checked={item.status} />
                                </Form.Check>
                            </div>
                        ))}
                        {console.log("=== for teacher")}
                    </Form>
                }
                {!!students && !!controlDateOptions == false && !!checkedStudent &&
                    <Form>
                        {students['students'].map((item) => (
                            <div key={`default-checkbox-ca`} className="mb-3">
                                <Form.Check type={'checkbox'}
                                            id={item.id}
                                            label={item.surname}
                                            name={"group1"}
                                            checked={checkedStudent[item.id]}
                                            onChange={checkboxChange}/>
                            </div>
                        ))}
                        {console.log("=== for capitan")}
                        {!!controlLastDate &&
                            <div>Последние данные внесены :{controlLastDate['controlLastDate']}</div>
                        }
                        <Button variant="primary" onClick={onSaveData}>Сохранить</Button>
                    </Form>
                }
            </Row>
            </Container>        
            


)
    
}

export default Book