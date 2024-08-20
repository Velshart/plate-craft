import './App.css';
import Button from "@mui/material/Button";
import { Checkbox, FormControl, FormControlLabel, FormGroup, InputLabel, MenuItem, Select } from "@mui/material";
import { useState } from "react";

function App() {
    const [formData, setFormData] = useState({
        selectedPlate: '',
        selectedLetter: '',
        selectedContent: '',
        error: '',
    });

    const [apiData, setApiData] = useState([]);

    const prefixes = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.split('');

    const onInputChange = (field) => (event) => {
        setFormData(prevData => ({
            ...prevData,
            [field]: event.target.value,
            error: '',
        }));
    };

    const onGenerateButtonClick = () => {
        const { selectedLetter, selectedPlate } = formData;
        if (!selectedLetter || !selectedPlate) {
            setFormData(prevData => ({ ...prevData, error: 'Some options are missing!' }));
            return;
        }
        fetchApiData();
    };

    const fetchApiData = () => {
        const { selectedPlate, selectedLetter } = formData;
        fetch(`http://localhost:8081/api/${selectedPlate}/${selectedLetter}`)
            .then(response => response.json())
            .then(data => setApiData(data))
            .catch(error => {
                console.error("An error occurred during fetching data", error);
                setFormData(prevData => ({ ...prevData, error: 'Failed to fetch data from the server.' }));
            });
    };

    return (
        <div className="App">
            <h2 className="App-header">
                <p>Plate Craft</p>

                <FormControl className="prefixes-form">
                    <InputLabel id="select-label" className="prefixes-form-input-label" sx={{ color: 'white' }}>
                        Select a custom license plate prefix used in your Voivodeship
                    </InputLabel>
                    <Select
                        labelId="select-label"
                        value={formData.selectedLetter}
                        onChange={onInputChange('selectedLetter')}
                    >
                        {prefixes.map(prefix => (
                            <MenuItem key={prefix} value={prefix}>
                                {prefix}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>

                <FormGroup row>
                    <FormControlLabel
                        control={<Checkbox checked={formData.selectedPlate === 'polish'} onChange={() => setFormData({ ...formData, selectedPlate: 'polish' })} />}
                        label="Polish plates"
                        sx={{ mr: 20 }}
                    />
                    <FormControlLabel
                        control={<Checkbox checked={formData.selectedPlate === 'english'} onChange={() => setFormData({ ...formData, selectedPlate: 'english' })} />}
                        label="English plates"
                    />
                </FormGroup>

                <div className="generate-button">
                    <Button variant="contained" onClick={onGenerateButtonClick}>
                        Generate
                    </Button>
                </div>

                <FormControl className="generated-plate-input-form">
                    <InputLabel id="content-select-label">Here are some plates you may like</InputLabel>
                    <Select
                        labelId="content-select-label"
                        value={formData.selectedContent}
                        onChange={onInputChange('selectedContent')}
                    >
                        {apiData.map((item, index) => (
                            <MenuItem key={index} value={item.content} disabled>
                                {item.content}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>

                {formData.error && <p className="error-message">{formData.error}</p>}
            </h2>

            <footer className="App-footer">
                <a href="https://github.com/Velshart/plate-craft" target="_blank" rel="noopener noreferrer">
                    Source code can be found here
                </a>
            </footer>
        </div>
    );
}

export default App;
