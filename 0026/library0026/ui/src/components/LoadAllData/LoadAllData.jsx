
import { Card} from "react-bootstrap";
export const LoadAllData = ({data, itemName, title, columnNameArray, columnKeyArray}) => { 


//<tr key={-12}>
  //                      {columnNameArray.map(n=> (<th>{n}</th>))}
    //                </tr> 

    return (<Card>
                <p>{title}</p>
                <table >
                    <thead>
                        <tr >
                            {columnNameArray.map((n, i)=> (<th key={n+i}>{n}</th>))}
                        </tr>
                    
                    </thead>
                    <tbody>
                    { !!data && !!data[itemName] &&
                        data[itemName].map((item, i) => (
                            <tr  key={i}>
                                {columnKeyArray.map((k, i) => (<td key={k+i}>{item[k]}</td>))}
                            </tr>
                        ))
                    }
                    </tbody>
                </table>
    </Card>)
}

